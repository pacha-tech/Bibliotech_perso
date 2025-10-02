package com.tp.controller.loans;

import com.tp.dao.DAOFactory;
import com.tp.model.Loan;
import com.tp.model.User;
import com.tp.service.LoanService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchLoanController extends HttpServlet {
    LoanService loanService;
    List<Loan> loans;

    public void init(){
        DAOFactory daoFactory = DAOFactory.getInstance();
        loanService = new LoanService(daoFactory);
        loans = new ArrayList<>();
    }

    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session == null )? null : (User) session.getAttribute("user");

        String searchType = request.getParameter("searchtype");
        String search = request.getParameter("search");

        assert currentUser != null;
        if(currentUser.getRole().equals("MEMBER")){
            if(searchType.equals("date")){
                try {
                    loans = loanService.findByDateAndUser(LocalDateTime.parse(search) , currentUser.getUser_id());
                    request.setAttribute("loans",loans);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if(searchType.equals("title")){
                try {
                    loans = loanService.findByBooktitleAndUser(search , currentUser.getUser_id());
                    request.setAttribute("loans",loans);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(searchType.equals("name")){
            try {
                loans = loanService.findByUsername(search);
                request.setAttribute("loans",loans);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (searchType.equals("title")) {
            try {
                loans = loanService.findByBooktitle(search);
                request.setAttribute("loans",loans);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                loans = loanService.findByDate(LocalDateTime.parse(search));
                request.setAttribute("loans",loans);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
