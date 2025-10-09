package com.tp.controller.books;

import com.tp.dao.DAOFactory;
import com.tp.model.Loan;
import com.tp.model.Reservation;
import com.tp.model.User;
import com.tp.model.generateID.GenerateIdLoans;
import com.tp.service.BookService;
import com.tp.service.LoanService;
import com.tp.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/returnBook")
public class ReturnBookController extends HttpServlet {

    private ReservationService reservationService;
    private static final Logger logger = LoggerFactory.getLogger(ReturnBookController.class);

    private  BookService bookService;
    private  LoanService loanService;
    private  Reservation reservation;
    private Loan loan = new Loan();
    private GenerateIdLoans G = new GenerateIdLoans();

    public void init(){
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reservationService = new ReservationService(daoFactory);
        bookService = new BookService(daoFactory);
        loanService = new LoanService(daoFactory);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String loanId = request.getParameter("loanId");

        if (loanId != null && !loanId.isEmpty()) {
            try {
                Loan loanToReturn = loanService.getLoanById(loanId);

                if (loanToReturn != null) {
                    boolean loanUpdated = loanService.updateLoanReturnDate(loanId, LocalDateTime.now());

                    if (loanUpdated) {

                        reservation = reservationService.getFirstReservation(loanToReturn.getBook_id());  // on verifie si il ya une reservation pour ce livre

                        if( reservation != null ){             // si oui on prends la premiere reservation

                            if(!loanService.CountLoanByUser(reservation.getUser_id())){
                                loan.setLoan_id(G.generateID());
                                loan.setUser_id(reservation.getUser_id());
                                loan.setBook_id(reservation.getBook_id());
                                loan.setBorrow_date(LocalDateTime.now());
                                loan.setDue_date(LocalDateTime.now().plusDays(14));

                                if(loanService.AddLoan(loan)){
                                    logger.info("Tranfert de reserver a emprunter chez "+reservation.getUser_name()+" avec succes");
                                }

                                if(reservationService.updateReservationStatus(reservation.getReservation_id(), "FULFILLED")){
                                    session.setAttribute("succes", "le livre que vous avez reserver vient d'etre disponible vous pouvez deja l'utiliser");
                                    logger.info("Changement du statut de la reservation en FULFILLED OK");
                                }else {
                                    logger.error("Erreur lors du changement du statut de la reservation en FULFILLED ");
                                }
                            }else {
                                logger.info("le user a deja trois emprunts");
                            }

                        }else{
                            boolean bookStatusUpdated = bookService.updateBookStatus(loanToReturn.getBook_id(), "disponible");
                            if(bookStatusUpdated){
                                logger.info("Changement du statut du livre en DISPONIBLE OK");
                            }else {
                                logger.error("Erreur lors du changement du statut du livre en DISPONIBLE ");
                            }
                        }

                        session.setAttribute("message", "Le livre a été rendu avec succès.");

                    } else {
                        session.setAttribute("error", "Impossible d’enregistrer le retour de l’emprunt.");
                    }
                } else {
                    session.setAttribute("error", "Emprunt introuvable.");
                }
            } catch (Exception e) {
                session.setAttribute("error", "Une erreur est survenue lors du retour du livre.");
                logger.error("Erreur lors du retour du livre");
            }
        } else {
            session.setAttribute("error", "ID d'emprunt invalide ou manquant.");
        }

        response.sendRedirect("memberListLoans");
    }
}
