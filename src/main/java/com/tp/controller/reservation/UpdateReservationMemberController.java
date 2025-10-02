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

@WebServlet("/memberUpdateReservation")
public class UpdateReservationMemberController extends HttpServlet {

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

                Reservation existingReservation = reservationService.findById(reservationId);

                if (existingReservation != null) {
                    boolean success = false;
                    if ("CANCELLED".equals(newStatus)) {
                        success = reservationService.fulfillReservation(reservationId);
                    }

                    if (success) {
                        session.setAttribute("message", "Le statut de la réservation a été mis à jour avec succès en '" + newStatus + "'.");
                    } else {
                        session.setAttribute("error", "Échec de la mise à jour du statut de la réservation.");
                    }
                } else {
                    session.setAttribute("error", "La réservation spécifiée n'existe pas.");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "ID de réservation invalide.");
                e.printStackTrace();
            }
        } else {
            session.setAttribute("error", "Données de mise à jour manquantes.");
        }
        response.sendRedirect("memberListReservations");
    }
}