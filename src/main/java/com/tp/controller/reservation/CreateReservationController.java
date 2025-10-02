package com.tp.controller.reservation;

import com.tp.dao.DAOFactory;
import com.tp.model.User;
import com.tp.service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/createReservation")
public class CreateReservationController extends HttpServlet {

    private ReservationService reservationService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reservationService = new ReservationService(daoFactory);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !currentUser.getRole().equals("MEMBER")) {
            response.sendRedirect("login");
            return;
        }

        String userId = request.getParameter("userId");
        String bookId = request.getParameter("bookId");

        if (bookId != null && !bookId.isEmpty()) {
            boolean success = reservationService.createReservation(userId, bookId);
            if (success) {
                request.setAttribute("message", "Livre réservé avec succès!");
            } else {
                request.setAttribute("error", "Erreur lors de la réservation : vous avez atteint la limite de réservations.");
            }
        }

        request.getRequestDispatcher("listBooks").forward(request, response);
    }
}