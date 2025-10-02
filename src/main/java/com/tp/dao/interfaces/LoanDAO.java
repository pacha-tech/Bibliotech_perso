package com.tp.dao.interfaces;

import com.tp.model.Loan;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanDAO {
    boolean AddLoan(Loan loan) throws Exception;
    List<Loan> getAllLoansByUser(String userId) throws Exception;
    List<Loan> getEveryLoanByUser(String user_id);
    List<Loan> getAllLoans() throws Exception;
    List<Loan> getAllActiveLoans() throws Exception;
    List<Loan> findByDate(LocalDateTime date) throws Exception;
    List<Loan> findByUsername(String user_name) throws Exception;
    List<Loan> findByBooktile(String book_title) throws Exception;
    List<Loan> findByDateAndByUser(LocalDateTime date , String user_id) throws Exception;
    List<Loan> findByBooktitleAndByUser(String book_title , String user_id) throws Exception;
    boolean updateLoanReturnDate(String loanId, LocalDateTime returnDate);
    Loan getLoanById(String loanId);
    boolean isBookBorrowedBy(String user_id , String book_id);
    boolean countLoanByUser(String user_id) throws Exception;
}
