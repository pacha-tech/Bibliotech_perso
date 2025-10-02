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

@WebServlet("/cancelReservation")
public class CancelReservationController extends HttpServlet {

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

        String reservationIdParam = request.getParameter("reservationId");

        if (reservationIdParam != null && !reservationIdParam.isEmpty()) {
            try {
                String reservationId = reservationIdParam;
                boolean success = reservationService.cancelReservation(reservationId);

                if (success) {
                    session.setAttribute("message", "Réservation annulé.");
                } else {
                    session.setAttribute("error", "Échec de l'annulation de la réservation.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de réservation invalide.");
            }
        }

        response.sendRedirect("memberListReservations");
    }
}