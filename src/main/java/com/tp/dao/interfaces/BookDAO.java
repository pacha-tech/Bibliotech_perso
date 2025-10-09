package com.tp.dao.interfaces;


import com.tp.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    void AddBook(Book book) throws SQLException;
    List<Book> findByTitle(String title) throws SQLException;
    List<Book> findByYear(int year) throws SQLException;
    List<Book> findByAuthor(String author) throws SQLException;
    List<Book> findByCategory(String category) throws SQLException;
    List<Book> findByDisponible() throws SQLException;
    List<Book> findByEmprunter() throws SQLException;
    List<Book> findByPopularity() throws SQLException;
    List<Book> findByRecent() throws SQLException;
    List<Book> findByOld() throws SQLException;
    List<Book> getAllBooks() throws SQLException ;
    void DeleteBook(String book_id) throws SQLException;
    void updateBook(Book book) throws SQLException;
    Book getBook(String book_id) throws SQLException;
    boolean updateBookStatus(String bookId, String status) throws SQLException;
    boolean AddLoanCountOfBook(String book_id) throws SQLException;
}
