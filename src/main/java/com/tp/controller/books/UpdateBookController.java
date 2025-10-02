package com.tp.controller.books;

import com.tp.dao.DAOFactory;
import com.tp.model.Book;
import com.tp.model.User;
import com.tp.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/updateBook")
public class UpdateBookController extends HttpServlet {
    BookService bookService;
    Book book;

    public void init(){
        DAOFactory daoFactory = DAOFactory.getInstance();
        bookService = new BookService(daoFactory);
        book = new Book();
    }

    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String book_id = request.getParameter("book_id");

        try {
            book = bookService.getBook(book_id);
        } catch (Exception e) {
            System.out.println("erreur lors de la recuperation de l'id du book a modifier");
        }

        try {
            bookService.updateBook(book);
            request.getSession().setAttribute("succes","Livre modifier avec succes");
        }catch (Exception e){
            request.getSession().setAttribute("error","Erreur lors de la modification du livre");
        }
        response.sendRedirect("listBooks");
    }
}
