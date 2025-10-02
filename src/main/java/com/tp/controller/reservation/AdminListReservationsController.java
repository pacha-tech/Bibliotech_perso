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

@WebServlet("/adminListReservations")
public class AdminListReservationsController extends HttpServlet {

    private ReservationService reservationService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reservationService = new ReservationService(daoFactory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            response.sendRedirect("login");
            return;
        }

        reservationService.cancelExpiredReservations();
        List<Reservation> reservations = reservationService.getAllReservations();

        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        String statusFilter = request.getParameter("status");

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            try {
                switch (searchType) {
                    case "userId":
                        reservations = reservationService.getReservationsByUserId(searchValue.trim());
                        break;
                    case "userName":
                        reservations = reservationService.getReservationsByUserName(searchValue.trim());
                        break;
                    case "bookId":
                        reservations = reservationService.getReservationsByBookId(searchValue.trim());
                        break;
                    case "bookName":
                        reservations = reservationService.getReservationsByBookName(searchValue.trim());
                        break;
                    default:
                        reservations = reservationService.getAllReservations();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (statusFilter != null && !statusFilter.isEmpty()) {
            reservations = reservationService.getReservationsByStatus(statusFilter);
        } else {
            reservations = reservationService.getAllReservations();
        }

        request.setAttribute("reservations", reservations);
        request.setAttribute("searchType", searchType);
        request.setAttribute("searchValue", searchValue);
        request.setAttribute("status", statusFilter);

        request.setAttribute("activePage", "reservations");
        request.getRequestDispatcher("/WEB-INF/Vues/reservation/adminReservationList.jsp").forward(request, response);
    }
}