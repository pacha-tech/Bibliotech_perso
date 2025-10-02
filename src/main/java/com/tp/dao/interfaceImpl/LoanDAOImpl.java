package com.tp.dao.interfaceImpl;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.LoanDAO;
import com.tp.model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LoanDAOImpl implements LoanDAO {

    private final DAOFactory daoFactory;

    public LoanDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean AddLoan(Loan loan) {
        String sql = "INSERT INTO loans (loan_id, user_id, book_id, borrow_date, due_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, loan.getLoan_id());
            stmt.setString(2, loan.getUser_id());
            stmt.setString(3, loan.getBook_id());
            stmt.setTimestamp(4, Timestamp.valueOf(loan.getBorrow_date()));
            stmt.setTimestamp(5, Timestamp.valueOf(loan.getDue_date()));
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Loan> getAllLoansByUser(String user_id) {
        List<Loan> loans = new ArrayList<>();
        String sql = " SELECT l.loan_id, l.user_id, l.book_id, l.borrow_date, l.due_date, l.return_date, b.title " +
                " FROM loans l " +
                " INNER JOIN books b ON l.book_id = b.book_id " +
                " WHERE l.user_id = ? AND b.status = 'emprunté' AND l.return_date IS NULL ";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> getEveryLoanByUser(String user_id) {
        List<Loan> loans = new ArrayList<>();
        String sql = " SELECT l.loan_id, l.user_id,u.name, l.book_id, l.borrow_date, l.due_date, l.return_date, b.title, b.status " +
                " FROM loans l " +
                " INNER JOIN users u ON l.user_id = u.user_id " +
                " INNER JOIN books b ON l.book_id = b.book_id WHERE u.user_id = ? ORDER BY borrow_date DESC";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = " SELECT L.loan_id, L.user_id , L.book_id , U.name, B.title, B.status , L.borrow_date, L.due_date, L.return_date " +
                " FROM loans L " +
                " INNER JOIN users U ON L.user_id = U.user_id " +
                " INNER JOIN books B ON L.book_id = B.book_id  ORDER BY borrow_date DESC";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Loan loan = new Loan(
                        rs.getString("loan_id"),
                        rs.getString("user_id"),
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getTimestamp("borrow_date").toLocalDateTime(),
                        rs.getTimestamp("due_date").toLocalDateTime(),
                        (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                );
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> getAllActiveLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = " SELECT L.loan_id, L.user_id, L.book_id, U.name, B.title, B.status, L.borrow_date, L.due_date, L.return_date " +
                " FROM loans L " +
                " INNER JOIN users U ON L.user_id = U.user_id " +
                " INNER JOIN books B ON L.book_id = B.book_id " +
                " WHERE L.return_date IS NULL " +
                " ORDER BY borrow_date DESC ";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Loan loan = new Loan(
                        rs.getString("loan_id"),
                        rs.getString("user_id"),
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getTimestamp("borrow_date").toLocalDateTime(),
                        rs.getTimestamp("due_date").toLocalDateTime(),
                        (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                );
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> findByDate(LocalDateTime date) {
        List<Loan> loans = new ArrayList<>();
        LocalDateTime startOfDay = date.with(LocalTime.MIN);
        LocalDateTime endOfDay = date.with(LocalTime.MAX);
        String sql = " SELECT L.loan_id, L.user_id, U.name, L.book_id, B.title, B.status, L.borrow_date, L.due_date, L.return_date" +
                " FROM loans L" +
                " INNER JOIN users U ON L.user_id = U.user_id" +
                " INNER JOIN books B ON L.book_id = B.book_id" +
                " WHERE L.borrow_date BETWEEN ? AND ? ";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(startOfDay));
            stmt.setTimestamp(2, Timestamp.valueOf(endOfDay));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> findByUsername(String user_name) {
        List<Loan> loans = new ArrayList<>();
        String sql = " SELECT l.loan_id, l.user_id,u.name, l.book_id, l.borrow_date, l.due_date, l.return_date, b.title, b.status " +
                " FROM loans l " +
                " INNER JOIN users u ON l.user_id = u.user_id " +
                " INNER JOIN books b ON l.book_id = b.book_id WHERE u.name = ? ORDER BY borrow_date DESC";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user_name);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> findByBooktile(String book_title) {
        List<Loan> loans = new ArrayList<>();
        String sql = " SELECT l.loan_id, l.user_id,u.name, l.book_id, l.borrow_date, l.due_date, l.return_date, b.title, b.status" +
                " FROM loans l " +
                " INNER JOIN users u ON l.user_id = u.user_id " +
                " INNER JOIN books b ON l.book_id = b.book_id WHERE b.title = ? ORDER BY borrow_date DESC";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book_title);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> findByDateAndByUser(LocalDateTime date, String user_id) {
        List<Loan> loans = new ArrayList<>();
        LocalDateTime startOfDay = date.with(LocalTime.MIN);
        LocalDateTime endOfDay = date.with(LocalTime.MAX);
        String sql = " SELECT L.loan_id, L.user_id, U.name, L.book_id, B.title, B.status , L.borrow_date, L.due_date, L.return_date "+
                " FROM loans L "+
                " INNER JOIN users U ON L.user_id = U.user_id "+
                " INNER JOIN books B ON L.book_id = B.book_id " +
                " WHERE L.borrow_date BETWEEN ? AND ? AND U.user_id = ? ";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(startOfDay));
            stmt.setTimestamp(2, Timestamp.valueOf(endOfDay));
            stmt.setString(3, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> findByBooktitleAndByUser(String book_title, String user_id) {
        List<Loan> loans = new ArrayList<>();
        String sql = " SELECT L.loan_id, L.user_id, U.name, L.book_id, B.title, B.status , L.borrow_date, L.due_date, L.return_date "+
                " FROM loans L "+
                " INNER JOIN users U ON L.user_id = U.user_id "+
                " INNER JOIN books B ON L.book_id = B.book_id " +
                " WHERE B.title = ? AND U.user_id = ? ";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book_title);
            stmt.setString(2, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public boolean updateLoanReturnDate(String loanId, LocalDateTime returnDate) {
        String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(returnDate));
            stmt.setString(2, loanId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Loan getLoanById(String loanId) {
        String sql = "SELECT * FROM loans WHERE loan_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, loanId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Loan(
                            rs.getString("loan_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getTimestamp("borrow_date").toLocalDateTime(),
                            rs.getTimestamp("due_date").toLocalDateTime(),
                            (rs.getTimestamp("return_date") != null) ? rs.getTimestamp("return_date").toLocalDateTime() : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isBookBorrowedBy(String user_id, String book_id) {
        String query = "SELECT COUNT(*) FROM loans WHERE book_id = ? AND user_id = ? AND return_date IS NULL ";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book_id);
            stmt.setString(2, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean countLoanByUser(String user_id) {
        String query = " SELECT COUNT(*) " +
                " FROM loans L " +
                " INNER JOIN books B ON B.book_id = L.book_id " +
                " WHERE L.user_id = ? AND B.status = 'emprunté' AND return_date IS NULL";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 2;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}