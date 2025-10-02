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
import java.util.stream.Collectors;

@WebServlet("/reservationHistory")
public class ReservationHistoryController extends HttpServlet {

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

        List<Reservation> reservations = reservationService.getReservationsByUserId(currentUser.getUser_id());

        String searchValue = request.getParameter("searchValue");
        String statusFilter = request.getParameter("status");
        String searchType = request.getParameter("searchType");

        if (statusFilter != null && !statusFilter.isEmpty()) {
            reservations = reservations.stream()
                    .filter(res -> res.getStatus().equalsIgnoreCase(statusFilter))
                    .collect(Collectors.toList());
        }

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            String finalSearchValue = searchValue.trim().toLowerCase();
            switch (searchType) {
                case "bookId":
                    reservations = reservations.stream()
                            .filter(res -> res.getBook_id().equalsIgnoreCase(finalSearchValue))
                            .collect(Collectors.toList());
                    break;
                case "bookName":
                    reservations = reservations.stream()
                            .filter(res -> res.getBook_title().toLowerCase().contains(finalSearchValue))
                            .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }

        request.setAttribute("reservations", reservations);
        request.setAttribute("searchType", searchType);
        request.setAttribute("searchValue", searchValue);
        request.setAttribute("status", statusFilter);
        request.getRequestDispatcher("/WEB-INF/Vues/reservation/reservationHistory.jsp").forward(request, response);
    }
}