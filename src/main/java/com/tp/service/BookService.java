package com.tp.service;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.BookDAO;
import com.tp.model.Book;
import com.tp.model.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookService {

    private final BookDAO bookDao;

    public BookService(DAOFactory daoFactory) {
        this.bookDao = daoFactory.getBookDAO();
    }

    public void addBook(Book book) throws Exception {
        bookDao.AddBook(book);
    }

    public Book getBook(String book_id) throws Exception {
        return bookDao.getBook(book_id);
    }

    public List<Book> getAllBook() throws Exception {
        return bookDao.getAllBooks();
    }

    public void deleteBook(String book_id) throws Exception {
        bookDao.DeleteBook(book_id);
    }

    public List<Book> findByTitle(String title) throws Exception {
        return bookDao.findByTitle(title);
    }

    public List<Book> findByYear(int year) throws Exception {
        return bookDao.findByYear(year);
    }

    public List<Book> findByAuthor(String author) throws Exception {
        return bookDao.findByAuthor(author);
    }

    public List<Book> findByCategory(String category) throws Exception {
        return bookDao.findByCategory(category);
    }

    public List<Book> findByDisponible() throws Exception {
        return bookDao.findByDisponible();
    }

    public List<Book> findByEmprunter() throws Exception {
        return bookDao.findByEmprunter();
    }

    public List<Book> findByPopularity() {
        return bookDao.findByPopularity();
    }
    public List<Book> findByRecent() {
        return bookDao.findByRecent();
    }
    public List<Book> findByOld() {
        return bookDao.findByOld();
    }

    public void updateBook(Book book) throws Exception {
        bookDao.updateBook(book);
    }

    public List<Book> verifyBookStatus(List<Book> Books , String user_id) throws Exception {
        List<Book> list = new ArrayList<>();
        DAOFactory daoFactory = DAOFactory.getInstance();

        List<Book> books = getAllBook();
        LoanService loanService = new LoanService(daoFactory);
        List<Loan> loans = loanService.getAllLoansByUser(user_id);
        for (Book book : books){
            for (Loan loan : loans){
                if(Objects.equals(book.getId_Book(), loan.getBook_id()) && !Objects.equals(book.getStatus(), "rendu")){
                    list.add(book);
                }
            }
        }

        for (Book book : books){
            if(!list.contains(book)){
                list.add(book);
            }
        }
        return list;
    }

    public boolean updateBookStatus(String bookId, String status) throws Exception {
        return bookDao.updateBookStatus(bookId, status);
    }

    public void AddLoanCountOfBook(String book_id) throws Exception {
        bookDao.AddLoanCountOfBook(book_id);
    }
}