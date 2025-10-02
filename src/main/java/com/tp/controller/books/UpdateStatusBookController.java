package com.tp.controller.books;

import com.tp.dao.DAOFactory;
import com.tp.model.Loan;
import com.tp.model.User;
import com.tp.model.generateID.GenerateIdLoans;
import com.tp.service.BookService;
import com.tp.service.LoanService;
import com.tp.service.ReservationService;
import com.tp.service.UserService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

@WebServlet("/status")
public class UpdateStatusBookController extends HttpServlet {

    BookService bookService;
    LoanService loanService;
    ReservationService reservationService;
    GenerateIdLoans G = new GenerateIdLoans();
    Loan loan;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reservationService = new ReservationService(daoFactory);
        bookService = new BookService(daoFactory);
        loanService = new LoanService(daoFactory);
        loan = new Loan();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;
        assert currentUser != null;
        request.setCharacterEncoding("UTF-8");

        String bookId = request.getParameter("id_book");
        String action = request.getParameter("action");

        System.out.println("Données reçues du client:");
        System.out.println("ID du livre: " + bookId);
        System.out.println("Action demandée: " + action);



        if(Objects.equals(action, "emprunté")){



            loan.setLoan_id(G.generateID());
            loan.setUser_id(currentUser.getUser_id());
            loan.setBook_id(bookId);
            loan.setBorrow_date(LocalDateTime.now());
            loan.setDue_date(LocalDateTime.now().plusDays(14));
            //loan.setReturn_date(null);

            try {
                int nbre;
                nbre = loanService.AddLoan(loan);
                if(nbre == 0){
                    session.setAttribute("error", "Vous avez deja trois emprunt en cours veillez d'abord remettre avant d'etre elligibe");
                }else if(nbre == 1){
                    session.setAttribute("succes", "livre Emprunter avec succes");
                    try {
                        bookService.updateBookStatus(bookId , action);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la mise a jour du status");
                        throw new RuntimeException(e);
                    }
                }else {
                    session.setAttribute("error", "erreur lors de l'emprunt veillez ressayer");
                }
            } catch (Exception e) {
                System.out.println("Erreur lors de l'insertion dans la table loan");
                throw new RuntimeException(e);
            }

            try {
                bookService.AddLoanCountOfBook(bookId);
            } catch (Exception e) {
                System.out.println("Erreur lors de l'incrementation du loan_count dans la table book");
                throw new RuntimeException(e);
            }

        }
        if (Objects.equals(action, "reserver")){
            int nbre ;
            try {
                nbre = reservationService.createReservationForInt(currentUser.getUser_id() , bookId);
                if(nbre == 0){
                    session.setAttribute("error", "Vous avez deja ce livre a votre possession");
                }else if (nbre == 1) {
                    session.setAttribute("error", "Vous avez deja Reserver ce livre");
                }else if(nbre == 2){
                    session.setAttribute("succes", "Livre Reserver avec succes");
                }else {
                    session.setAttribute("error", "Nombres maximum de reservation atteint.");
                }
            }catch (Exception e){
                System.err.println("l'utilisateur a deja ce livre a sa possession");
            }
        }

        response.sendRedirect("listBooks");

    }
}
