package com.tp.service;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.LoanDAO;
import com.tp.model.Loan;

import java.time.LocalDateTime;
import java.util.List;

public class LoanService {
    private final LoanDAO loanDAO;

    public LoanService(DAOFactory daoFactory){
        this.loanDAO = daoFactory.getLoanDAO();
    }

    public int AddLoan(Loan loan) throws Exception {
        if(loanDAO.countLoanByUser(loan.getUser_id())){
            return 0 ; //l'utilisateur a deja trois emprunt en cours
        }else if(loanDAO.AddLoan(loan)){
            return 1;   //l'utilisateur n'a pas trois emprunt  en cours
        }else {
            return 2;  //Erreur interne
        }
    }

    /*
    public void DeleteLoan(String loan_id) throws Exception {
        loanDAO.DeleteLoan(loan_id);
    }
    */

    public List<Loan> getAllLoansByUser(String user_id) throws Exception {
        return loanDAO.getAllLoansByUser(user_id);
    }

    public List<Loan> getEveryLoanByUser(String user_id) throws Exception {
        return loanDAO.getEveryLoanByUser(user_id);
    }

    public List<Loan> getAllLoans() throws Exception {
        return loanDAO.getAllLoans();
    }

    public List<Loan> getAllActiveLoans() throws Exception {
        return loanDAO.getAllActiveLoans();
    }

    public List<Loan> findByDate(LocalDateTime date) throws Exception {
        return loanDAO.findByDate(date);
    }

    public List<Loan> findByUsername(String user_name) throws Exception {
        return loanDAO.findByUsername(user_name);
    }

    public List<Loan> findByBooktitle(String book_title) throws Exception {
        return loanDAO.findByBooktile(book_title);
    }

    public List<Loan> findByDateAndUser(LocalDateTime date , String user_id) throws Exception {
        return loanDAO.findByDateAndByUser(date , user_id);
    }

    public List<Loan> findByBooktitleAndUser(String book_title , String user_id) throws Exception {
        return loanDAO.findByBooktitleAndByUser(book_title , user_id);
    }

    public boolean updateLoanReturnDate(String userId, LocalDateTime now) {
        return  loanDAO.updateLoanReturnDate(userId, now);
    }

    public Loan getLoanById(String loanId){
        return loanDAO.getLoanById(loanId);
    }

    public boolean isBookBorrowedBy(String user_id , String book_id){
        return loanDAO.isBookBorrowedBy(user_id , book_id);
    }
}
