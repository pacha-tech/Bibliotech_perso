package com.tp.dao;

import com.tp.dao.interfaceImpl.BookDAOImpl;
import com.tp.dao.interfaceImpl.LoanDAOImpl;
import com.tp.dao.interfaceImpl.ReservationDAOImpl;
import com.tp.dao.interfaceImpl.UserDAOImpl;
import com.tp.dao.interfaces.BookDAO;
import com.tp.dao.interfaces.LoanDAO;
import com.tp.dao.interfaces.ReservationDAO;
import com.tp.dao.interfaces.UserDAO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pacha12345";

    private static DAOFactory instance = null;
    private final HikariDataSource dataSource;

    private DAOFactory() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(30);

        this.dataSource = new HikariDataSource(config);
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public BookDAO getBookDAO() {
        return new BookDAOImpl(this);
    }

    public UserDAO getUserDAO() {
        return new UserDAOImpl(this);
    }

    public LoanDAO getLoanDAO() {
        return new LoanDAOImpl(this);
    }

    public ReservationDAO getReservationDAO() {
        return new ReservationDAOImpl(this);
    }
}