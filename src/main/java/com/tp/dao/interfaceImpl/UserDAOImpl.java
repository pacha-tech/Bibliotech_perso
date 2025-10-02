package com.tp.dao.interfaceImpl;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.UserDAO;
import com.tp.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements UserDAO {

    private final DAOFactory daoFactory;

    public UserDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean addUser(User user){
        String query = "INSERT INTO users (user_id, name, surname, tel_num, email, password, role, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean success = false;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {

            stmt.setString(1, user.getUser_id());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setInt(4,user.getTel_num());
            stmt.setString(5,user.getEmail());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getRole());
            stmt.setTimestamp(8, Timestamp.valueOf(user.getRegistration_date()));

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        boolean success = false;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {

            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getUser_id());

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean deleteUser(String userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        boolean success = false;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {

            stmt.setString(1,userId);

            int deleted = stmt.executeUpdate();
            if (deleted > 0) {
                success = true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public User findById(String userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        User user = null;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {

            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getString("user_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getInt("tel_num"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getTimestamp("registration_date").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur par ID : " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findByname(String name) {
        String query = "SELECT * FROM users WHERE name LIKE ?";
        List<User> userList = new ArrayList<>();

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getInt("tel_num"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getTimestamp("registration_date").toLocalDateTime()
                    );
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par nom : " + e.getMessage());
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<User> getAllMembers() {
        String query = "SELECT * FROM users WHERE role = ? ORDER BY registration_date ";
        List<User> userList = new ArrayList<>();

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {

            stmt.setString(1, "MEMBER");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getInt("tel_num"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getTimestamp("registration_date").toLocalDateTime()
                    );
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des membres : " + e.getMessage());
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public int countMembers() {
        String query = "SELECT COUNT(*) FROM users WHERE role = 'MEMBER'";
        int count = 0;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
