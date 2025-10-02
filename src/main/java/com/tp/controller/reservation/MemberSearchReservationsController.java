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

@WebServlet("/memberSearchReservations")
public class MemberSearchReservationsController extends HttpServlet {

    private ReservationService reservationService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reservationService = new ReservationService(daoFactory);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String searchValue = request.getParameter("searchValue");
        List<Reservation> searchResults = null;

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            searchResults = reservationService.getReservationsByUserIdAndBookName(currentUser.getUser_id());

            request.setAttribute("searchResults", searchResults);
            request.getRequestDispatcher("/WEB-INF/reservations.jsp").forward(request, response);
        }
    }
}