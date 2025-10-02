package com.tp.controller.loans;

import com.tp.dao.DAOFactory;
import com.tp.model.Loan;
import com.tp.model.User;
import com.tp.model.generateID.GenerateIdLoans;
import com.tp.service.LoanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet("/addLoan")
public class AddLoanController extends HttpServlet {
    LoanService loanService;
    GenerateIdLoans generateIdLoans;
    Loan loan;

    public void init(){
        DAOFactory daoFactory = DAOFactory.getInstance();
        loanService = new LoanService(daoFactory);
        generateIdLoans = new GenerateIdLoans();
        loan = new Loan();
    }

    protected void doGet(HttpServletRequest request , HttpServletResponse response ) throws ServletException , IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session == null) ? null : (User) session.getAttribute("user");

        assert currentUser != null;

        loan.setLoan_id(generateIdLoans.generateID());
        loan.setUser_id(currentUser.getUser_id());
        loan.setBook_id(request.getParameter("book_id"));
        loan.setBorrow_date(LocalDateTime.now());
        loan.setDue_date(LocalDateTime.now().plusDays(14));
        loan.setReturn_date(null);

        try {
            loanService.AddLoan(loan);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.err.println("Erreur lors de l'emprunt");
        }
    }
}
