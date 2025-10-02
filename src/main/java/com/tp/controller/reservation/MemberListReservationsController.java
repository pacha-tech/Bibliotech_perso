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
import java.util.List;

@WebServlet("/memberListReservations")
public class MemberListReservationsController extends HttpServlet {

    private ReservationService reservationService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reservationService = new ReservationService(daoFactory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        reservationService.cancelExpiredReservations();
        List<Reservation> reservations = reservationService.getActiveReservationsByUserId(currentUser.getUser_id());
        request.setAttribute("reservations", reservations);
        request.setAttribute("activePage", "reservation");
        System.out.println("reservation du user "+currentUser.getName()+" est "+reservations.size());
        request.getRequestDispatcher("/WEB-INF/Vues/reservation/memberReservationList.jsp").forward(request, response);
    }
}
