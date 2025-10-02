package com.tp.dao.interfaces;

import com.tp.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationDAO {

    boolean addReservation(Reservation reservation);
    boolean updateReservation(Reservation reservation);
    Reservation findById(String reservationId);
    List<Reservation> findByUserId(String userId);
    List<Reservation> findByUserName(String name);
    List<Reservation> findByBookId(String bookId);
    List<Reservation> findByBookName(String bookName);
    List<Reservation> findByStatus(String status);
    List<Reservation> getAllReservations();
    List<Reservation> findActiveByUserId(String userId);
    List<Reservation> findByUserIdAndBookName(String userId, String bookName);
    List<Reservation> findExpiredReservations();
    int countReservations();
    boolean isTwoReservationByBook(String user_id , String book_id);
    Reservation getFirstReservation(String book_id);
}