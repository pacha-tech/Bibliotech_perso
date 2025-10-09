package com.tp.service;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.BookDAO;
import com.tp.model.Book;
import com.tp.model.Loan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookService {

    private final BookDAO bookDao;

    public BookService(DAOFactory daoFactory) {
        this.bookDao = daoFactory.getBookDAO();
    }

    public void addBook(Book book) throws SQLException {
        bookDao.AddBook(book);
    }

    public Book getBook(String book_id) throws SQLException {
        return bookDao.getBook(book_id);
    }

    public List<Book> getAllBook() throws SQLException {
        return bookDao.getAllBooks();
    }

    public void deleteBook(String book_id) throws SQLException {
        bookDao.DeleteBook(book_id);
    }

    public List<Book> findByTitle(String title) throws SQLException {
        return bookDao.findByTitle(title);
    }

    public List<Book> findByYear(int year) throws SQLException {
        return bookDao.findByYear(year);
    }

    public List<Book> findByAuthor(String author) throws SQLException {
        return bookDao.findByAuthor(author);
    }

    public List<Book> findByCategory(String category) throws SQLException {
        return bookDao.findByCategory(category);
    }

    public List<Book> findByDisponible() throws SQLException {
        return bookDao.findByDisponible();
    }

    public List<Book> findByEmprunter() throws SQLException {
        return bookDao.findByEmprunter();
    }

    public List<Book> findByPopularity() throws SQLException {
        return bookDao.findByPopularity();
    }
    public List<Book> findByRecent() throws SQLException {
        return bookDao.findByRecent();
    }
    public List<Book> findByOld() throws SQLException {
        return bookDao.findByOld();
    }

    public void updateBook(Book book) throws SQLException {
        bookDao.updateBook(book);
    }

    public boolean updateBookStatus(String bookId, String status) throws SQLException {
        return bookDao.updateBookStatus(bookId, status);
    }

    public boolean AddLoanCountOfBook(String book_id) throws SQLException {
        return bookDao.AddLoanCountOfBook(book_id);
    }
    public String getPathBookImage(String book_id) throws SQLException {
        Book book = bookDao.getBook(book_id);
        return book.getImage();
    }
}