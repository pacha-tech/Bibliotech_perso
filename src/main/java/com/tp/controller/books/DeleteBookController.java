package com.tp.controller.books;

import com.tp.dao.DAOFactory;
import com.tp.model.User;
import com.tp.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteBook")
public class DeleteBookController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DeleteBookController.class);

    private BookService bookService;

    public void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.bookService = new BookService(daoFactory);
        logger.info("DeleteBookController initialisé.");
    }


    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {
        logger.info("Requête de suppression de livre reçue.");

        HttpSession session = request.getSession(false);
        User currentUser = (session == null) ? null : (User) session.getAttribute("user");
        String bookId = request.getParameter("id_book");

        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            logger.warn("Tentative d'accès non autorisée par un utilisateur : ID de livre = {}", bookId);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé. Vous n'avez pas les droits d'administrateur.");
            return;
        }

        try {
            logger.info("Tentative de suppression du livre avec l'ID : {}", bookId);
            bookService.deleteBook(bookId);

            logger.info("Livre avec l'ID {} supprimé avec succès.", bookId);
            session.setAttribute("succes", "Livre supprimé avec succès.");
            
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du livre avec l'ID : {}.", bookId, e);
            session.setAttribute("error", "Erreur lors de la suppression.");
        }

        response.sendRedirect("listBooks");
    }

}
