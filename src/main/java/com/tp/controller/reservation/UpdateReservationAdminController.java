package com.tp.controller.reservation;

import com.tp.dao.DAOFactory;
import com.tp.model.Reservation;
import com.tp.model.User;
import com.tp.service.ReservationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/adminUpdateReservation")
public class UpdateReservationAdminController extends HttpServlet {

    private ReservationService reservationService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reservationService = new ReservationService(daoFactory);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            response.sendRedirect("login");
            return;
        }

        String reservationIdParam = request.getParameter("reservationId");
        String newStatus = request.getParameter("status");

        if (reservationIdParam != null && !reservationIdParam.isEmpty() && newStatus != null && !newStatus.isEmpty()) {
            try {
                String reservationId = reservationIdParam;

                boolean success = reservationService.updateReservationStatus(reservationId,newStatus);

                if (success) {
                    session.setAttribute("message", "Réservation mise à jour avec succès au statut : " + newStatus);
                } else {
                    session.setAttribute("error", "Erreur lors de la mise à jour de la réservation.");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "ID de réservation invalide.");
            }
        } else {
            session.setAttribute("error", "Données de mise à jour manquantes.");
        }
        response.sendRedirect("adminListReservations");
    }
}