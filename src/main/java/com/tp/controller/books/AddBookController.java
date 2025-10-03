package com.tp.controller.books;

import com.tp.dao.DAOFactory;
import com.tp.model.Book;
import com.tp.model.User;
import com.tp.model.generateID.GenerateIdBooks;
import com.tp.service.BookService;
import com.tp.service.FileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Year; // Importation pour obtenir l'année actuelle

@WebServlet("/addBook")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class AddBookController extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploaded_books";
    private static final Logger logger = LoggerFactory.getLogger(AddBookController.class);

    private BookService bookService;
    private GenerateIdBooks ID;
    private FileUploader fileUploader;

    @Override
    public void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.bookService = new BookService(daoFactory);
        this.ID = new GenerateIdBooks();
        this.fileUploader = new FileUploader(UPLOAD_DIRECTORY);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            logger.info("Tentative d'ajout de livre par un utilisateur non autoriser");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/Vues/books/AddBooks.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }


        String title = request.getParameter("title");
        String yearString = request.getParameter("year");
        String author = request.getParameter("author");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        Part filePart = request.getPart("image");
        String imageFileNameInBD = "";

        try {
            imageFileNameInBD = fileUploader.handleFileUpload(filePart, getServletContext().getRealPath("/"));
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Erreur lors de l'envoi de l'image.");
            response.sendRedirect("listBooks");
            return;
        }

        Integer year = null;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "L'année doit être un nombre valide.");
            response.sendRedirect("listBooks");
            return;
        }


        int currentYear = Year.now().getValue();
        if (title != null && !title.trim().isEmpty() && author != null && !author.trim().isEmpty() && category != null && !category.trim().isEmpty() && description != null && !description.trim().isEmpty() && year != null && year > 1950 && year <= currentYear) {

            Book book = new Book();
            book.setId_Book(ID.generateID());
            book.setTitle(title);
            book.setAuthor(author);
            book.setYear(year);
            book.setImage(imageFileNameInBD);
            book.setCategory(category);
            book.setDescription(description);
            book.setStatus("disponible");
            book.setLoan_count(0);

            try {
                bookService.addBook(book);
                logger.info("livre ajouter avec succes");
                session.setAttribute("succes", "Livre ajouté avec succès.");
            } catch (SQLException e) {
                logger.error("Erreur lors de l'ajout du livre");
                session.setAttribute("error", "Erreur lors de l'ajout du livre.");
            }
            response.sendRedirect("listBooks");
        } else {
            session.setAttribute("error", "Erreur lors de l'ajout : veuillez vérifier que tous les champs sont remplis et que l'année est valide (supérieure à 1950 et inférieure ou égale à l'année en cours).");
            response.sendRedirect("listBooks");
        }
    }
}
