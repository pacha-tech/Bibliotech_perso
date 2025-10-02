package com.tp.controller.user;

import com.tp.dao.DAOFactory;
import com.tp.model.User;
import com.tp.service.BookService;
import com.tp.service.LoanService;
import com.tp.service.ReservationService;
import com.tp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List; // Import the List class
import com.tp.model.Book; // Import Book model
import com.tp.model.Loan; // Import Loan model
import com.tp.model.Reservation; // Import Reservation model

@WebServlet("/adminDashboard")
public class AdminDashboardController extends HttpServlet {

    private UserService userService;
    private ReservationService reservationService;
    private BookService bookService;
    private LoanService loanService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.userService = new UserService(daoFactory);
        this.reservationService = new ReservationService(daoFactory);
        this.bookService = new BookService(daoFactory);
        this.loanService = new LoanService(daoFactory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect("login");
            return;
        }

        try {
            int memberCount = userService.countMembers();
            int reservationCount = reservationService.countReservations();
            int bookCount = bookService.getAllBook().size();
            int loanCount = loanService.getAllActiveLoans().size();

            request.setAttribute("bookCount", bookCount);
            request.setAttribute("loanCount", loanCount);
            request.setAttribute("memberCount", memberCount);
            request.setAttribute("reservationCount", reservationCount);

            List<Book> popularBooks = bookService.findByPopularity();
            List<Loan> currentLoans = loanService.getAllActiveLoans();
            List<Reservation> activeReservations = reservationService.getActiveReservations();

            request.setAttribute("popularBooks", popularBooks);
            request.setAttribute("currentLoans", currentLoans);
            request.setAttribute("activeReservations", activeReservations);

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des données pour le tableau de bord.");
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors du chargement des données. Veuillez réessayer.");
        }

        request.setAttribute("activePage", "accueil");
        request.getRequestDispatcher("/WEB-INF/Vues/admin/adminDashboard.jsp").forward(request, response);
    }
}