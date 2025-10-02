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
import java.util.regex.Pattern;

@WebServlet("/addUser")
public class AddUserController extends HttpServlet {

    private UserService userService;

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.userService = new UserService(daoFactory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser != null && currentUser.getRole().equals("ADMIN")) {
            this.getServletContext().getRequestDispatcher("/WEB-INF/Vues/admin/addMember.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String tel_num_str = request.getParameter("tel_num");
        String email = request.getParameter("email");

        if (name == null || name.trim().isEmpty() ||
                surname == null || surname.trim().isEmpty() ||
                tel_num_str == null || tel_num_str.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {

            session.setAttribute("error", "Tous les champs sont obligatoires.");
            response.sendRedirect("manageUsers");
            return;
        }

        String regexTel = "^6[0-9]{8}$";
        if (!Pattern.matches(regexTel, tel_num_str)) {
            session.setAttribute("error", "Le numéro de téléphone n'est pas au format correct (ex. 6xxxxxxxx).");
            response.sendRedirect("manageUsers");
            return;
        }

        String regexEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!Pattern.matches(regexEmail, email)) {
            session.setAttribute("error", "L'adresse email n'est pas au format correct (ex. mike@gmail.com).");
            response.sendRedirect("manageUsers");
            return;
        }

        int tel_num = Integer.parseInt(tel_num_str);


        boolean success = userService.createAndAddUser(name, surname, tel_num, email);

        if (success) {
            session.setAttribute("message", "Utilisateur ajouté avec succès.");
        } else {
            session.setAttribute("error", "Erreur lors de l'ajout de l'utilisateur. L'utilisateur existe peut-être déjà.");
        }

        response.sendRedirect("manageUsers");
    }
}