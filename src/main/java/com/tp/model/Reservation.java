package com.tp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private String reservation_id;
    private String user_id;
    private String book_id;
    private String user_name;
    private String book_title;
    private LocalDateTime reservation_date;
    private LocalDateTime expire_date;
    private String status;

    // Simple Constructeur

    public Reservation(){
        this.reservation_id = "";
        this.user_id = "";
        this.book_id = "";
        this.reservation_date = null;
        this.expire_date = null;
        this.status = "";
    }

    // Constructeur pour les opérations d'ajout'
    public Reservation(String reservation_id,String user_id, String book_id, LocalDateTime reservation_date,  String status) {
        this.reservation_id = reservation_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.reservation_date = reservation_date;
        this.expire_date = reservation_date.plusDays(2);
        this.status = status;
    }

    // Nouveau constructeur pour les requêtes de recherche/affichage
    public Reservation(String reservation_id, String userId, String bookId, String userName, String bookTitle, LocalDateTime reservation_date, LocalDateTime expire_date, String status) {
        this.reservation_id = reservation_id;
        this.user_id = userId;
        this.book_id = bookId;
        this.user_name = userName;
        this.book_title = bookTitle;
        this.reservation_date = reservation_date;
        this.expire_date = expire_date;
        this.status = status;
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getBook_title() {
        return book_title;
    }


    public LocalDateTime getReservation_date() {
        return reservation_date;
    }

    public LocalDateTime getExpire_date() {
        return expire_date;
    }

    public String getStatus() {
        return status;
    }
    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setReservation_date(LocalDateTime reservation_date) {
        this.reservation_date = reservation_date;
    }

    public void setexpire_date(LocalDateTime expire_date) {
        this.expire_date = expire_date;
    }

    public String getFormattedDateRegister() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return reservation_date.format(formatter);
    }

    public String getFormattedDueRegister() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return expire_date.format(formatter);
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
