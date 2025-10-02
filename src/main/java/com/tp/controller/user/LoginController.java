package com.tp.controller.user;

import com.tp.dao.DAOFactory;
import com.tp.model.User;
import com.tp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserService userService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.userService = new UserService(daoFactory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Vues/auth/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String password = request.getParameter("password");

        User authenticatedUser = userService.authenticate(userId, password);

        if (authenticatedUser != null) {
            // Bloquer toute session existante pour empeher la contamination.
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("user", authenticatedUser);

            if ("0000".equals(password)) {
                request.getRequestDispatcher("/WEB-INF/Vues/auth/changePassword.jsp").forward(request, response);
            } else {
                if ("ADMIN".equals(authenticatedUser.getRole())) {
                    response.sendRedirect("adminDashboard");
                } else {
                    response.sendRedirect("memberDashboard");
                }
            }
        } else {
            request.setAttribute("error", "ID ou mot de passe incorrect.");
            request.getRequestDispatcher("/WEB-INF/Vues/auth/login.jsp").forward(request, response);
        }
    }
}