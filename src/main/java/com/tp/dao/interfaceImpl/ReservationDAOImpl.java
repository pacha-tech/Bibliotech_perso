package com.tp.dao.interfaceImpl;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.ReservationDAO;
import com.tp.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    private final DAOFactory daoFactory;

    public ReservationDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean addReservation(Reservation reservation) {
        String checkSql = "SELECT COUNT(*) FROM reservations WHERE user_id = ? AND status = 'ACTIVE' FOR UPDATE";
        String insertSql = "INSERT INTO reservations (reservation_id, user_id, book_id, reservation_date, expire_date, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connexion = daoFactory.getConnection()) {
            connexion.setAutoCommit(false);

            int activeCount = 0;
            try (PreparedStatement checkStmt = connexion.prepareStatement(checkSql)) {
                checkStmt.setString(1, reservation.getUser_id());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        activeCount = rs.getInt(1);
                    }
                }
            }

            if (activeCount >= 3) {
                connexion.rollback();
                return false;
            }

            try (PreparedStatement insertStmt = connexion.prepareStatement(insertSql)) {
                insertStmt.setString(1, reservation.getReservation_id());
                insertStmt.setString(2, reservation.getUser_id());
                insertStmt.setString(3, reservation.getBook_id());
                insertStmt.setTimestamp(4, Timestamp.valueOf(reservation.getReservation_date()));
                insertStmt.setTimestamp(5, Timestamp.valueOf(reservation.getExpire_date()));
                insertStmt.setString(6, reservation.getStatus());

                int rows = insertStmt.executeUpdate();
                connexion.commit();
                return rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        String query = "UPDATE reservations SET status = ? WHERE reservation_id = ?";
        boolean success = false;
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, reservation.getStatus());
            stmt.setString(2, reservation.getReservation_id());
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public Reservation findById(String reservationId) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date,r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE r.reservation_id = ?";
        Reservation reservation = null;
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, reservationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    public List<Reservation> findByUserId(String userId) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE r.user_id = ? ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> findByUserName(String name) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE u.name LIKE ? ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> findByBookId(String bookId) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE r.book_id = ? ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> findByBookName(String bookName) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE b.title LIKE ? ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, "%" + bookName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> findByStatus(String status) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE r.status = ? ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> getAllReservations() {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getString("reservation_id"),
                        rs.getString("user_id"),
                        rs.getString("book_id"),
                        rs.getString("user_name"),
                        rs.getString("book_title"),
                        rs.getTimestamp("reservation_date").toLocalDateTime(),
                        rs.getTimestamp("expire_date").toLocalDateTime(),
                        rs.getString("status")
                );
                list.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> findByUserIdAndBookName(String userId, String bookName) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE r.user_id = ? AND b.title LIKE ? AND r.status = ? ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, "%" + bookName + "%");
            stmt.setString(3,"ACTIVE");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> findActiveByUserId(String userId) {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.expire_date, r.status FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id WHERE r.user_id = ? AND r.status = ? ORDER BY r.reservation_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, "ACTIVE");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> findExpiredReservations() {
        String query = "SELECT r.reservation_id, r.user_id, r.book_id, u.name AS user_name, b.title AS book_title, r.reservation_date, r.status, r.expire_date " +
                "FROM reservations r JOIN users u ON r.user_id = u.user_id JOIN books b ON r.book_id = b.book_id " +
                "WHERE r.status = 'ACTIVE' AND r.expire_date < ?";
        List<Reservation> expiredReservations = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("book_id"),
                            rs.getString("user_name"),
                            rs.getString("book_title"),
                            rs.getTimestamp("reservation_date").toLocalDateTime(),
                            rs.getTimestamp("expire_date").toLocalDateTime(),
                            rs.getString("status")
                    );
                    expiredReservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expiredReservations;
    }

    @Override
    public int countReservations() {
        String query = "SELECT COUNT(*) FROM reservations WHERE status = 'ACTIVE'";
        int count = 0;
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean isTwoReservationByBook(String user_id, String book_id) {
        String query = "SELECT COUNT(*) FROM reservations WHERE book_id = ? AND user_id = ? AND status = 'ACTIVE' ";
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
    public Reservation getFirstReservation(String bookId) {
        Reservation reservation = null;
        String sql = "SELECT * FROM reservations WHERE book_id = ? AND status = 'ACTIVE' AND expire_date > NOW() ORDER BY reservation_date ASC LIMIT 1 ";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reservation = new Reservation(
                            resultSet.getString("reservation_id"),
                            resultSet.getString("user_id"),
                            resultSet.getString("book_id"),
                            resultSet.getTimestamp("reservation_date").toLocalDateTime(),
                            resultSet.getString("status")
                    );
                    return reservation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}