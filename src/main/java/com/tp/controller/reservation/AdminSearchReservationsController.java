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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/adminSearchReservations")
public class AdminSearchReservationsController extends HttpServlet {

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

        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        List<Reservation> searchResults = new ArrayList<>();

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            try {
                switch (searchType) {
                    case "userId":
                        searchResults = reservationService.getReservationsByUserId(searchValue.trim());
                        break;
                    case "userName":
                        searchResults = reservationService.getReservationsByUserName(searchValue.trim());
                        break;
                    case "bookId":
                        searchResults = reservationService.getReservationsByBookId(searchValue.trim());
                        break;
                    case "bookName":
                        searchResults = reservationService.getReservationsByBookName(searchValue.trim());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("reservations", searchResults);
        request.getRequestDispatcher("/WEB-INF/Vues/reservation/searchResult.jsp").forward(request, response);
    }
}