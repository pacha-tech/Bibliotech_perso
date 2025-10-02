package com.tp.dao.interfaces;


import com.tp.model.Book;

import java.util.List;

public interface BookDAO {
    void AddBook(Book book) throws Exception;
    List<Book> findByTitle(String title) throws Exception;
    List<Book> findByYear(int year) throws Exception;
    List<Book> findByAuthor(String author) throws Exception;
    List<Book> findByCategory(String category) throws Exception;
    List<Book> findByDisponible() throws Exception;
    List<Book> findByEmprunter() throws Exception;
    List<Book> findByPopularity();
    List<Book> findByRecent();
    List<Book> findByOld();
    List<Book> getAllBooks() throws Exception ;
    void DeleteBook(String book_id) throws Exception;
    void updateBook(Book book) throws Exception;
    Book getBook(String book_id) throws Exception;
    boolean updateBookStatus(String bookId, String status);
    void AddLoanCountOfBook(String book_id) throws Exception;
}
