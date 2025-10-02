package com.tp.model;

import java.time.LocalDateTime;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class Loan {

    private String loan_id;
    private String user_id;
    private String book_id;
    private String book_title;
    private String username;
    private String status;
    private LocalDateTime borrow_date;
    private LocalDateTime due_date;
    private LocalDateTime return_date;

    public Loan( ) {
        this.loan_id = "";
        this.user_id = "";
        this.book_id = "";
        this.borrow_date = null ;
        this.due_date = null;
        this.return_date = null;
    }

    public Loan(String loan_id, String user_id, String book_id, LocalDateTime borrow_date, LocalDateTime due_date, LocalDateTime return_date) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
    }

    // Nouveau constructeur avec le titre du livre
    public Loan(String loan_id, String user_id, String book_id, String book_title , LocalDateTime borrow_date, LocalDateTime due_date, LocalDateTime return_date) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.book_title = book_title;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
    }

    // Nouveau constructeur avec le titre du livre et status
    public Loan(String loan_id, String user_id, String book_id, String book_title, String status , LocalDateTime borrow_date, LocalDateTime due_date, LocalDateTime return_date) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.book_title = book_title;
        this.status = status;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
    }

    // Nouveau constructeur avec le username et le book-title et status
    public Loan(String loan_id, String user_id, String book_id, String book_title, String username , String status , LocalDateTime borrow_date, LocalDateTime due_date, LocalDateTime return_date) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.book_title = book_title;
        this.username = username;
        this.status = status;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getBook_title() { return book_title;}

    public String getUsername() { return username; }

    public String getStatus() { return status; }

    public LocalDateTime getBorrow_date() {
        return borrow_date;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public LocalDateTime getReturn_date() {
        return return_date;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setBook_title(String book_title) {this.book_title = book_title;}

    public void setUsername(String username) { this.username = username; }

    public void setStatus(String status) { this.status = status; }

    public void setBorrow_date(LocalDateTime borrow_date) {
        this.borrow_date = borrow_date;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
    }

    public void setReturn_date(LocalDateTime return_date) {
        this.return_date = return_date;
    }

    public String getFormattedBorrowDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return borrow_date.format(formatter);
    }

    public String getFormattedDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return due_date.format(formatter);
    }

    public String getFormattedReturnDate() {
        if (return_date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return return_date.format(formatter);
        }
        return "Non retourné";
    }
}
