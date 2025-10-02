package com.tp.service;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.UserDAO;
import com.tp.model.User;
import com.tp.model.generateID.GenerateUserID;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService(DAOFactory daoFactory) {
        this.userDAO = daoFactory.getUserDAO();
    }

    public boolean createAndAddUser(String name, String surname, int telNum, String email) {
        GenerateUserID generator = new GenerateUserID();
        String userId = generator.generateID();
        User user = new User(
                userId,
                name,
                surname,
                telNum,
                email,
                "0000",
                "MEMBER",
                LocalDateTime.now()
        );

        return userDAO.addUser(user);
    }

    public User authenticate(String userId, String password) {
        User user = userDAO.findById(userId);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    public boolean updateUser(User user)  {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(String userId) {
        return userDAO.deleteUser(userId);
    }

    public User findUserById(String userId) {
        return userDAO.findById(userId);
    }

    public List<User> findUserByName(String name) {
        return userDAO.findByname(name);
    }

    public List<User> getAllMembers() {
        return userDAO.getAllMembers();
    }

    public int countMembers() {
        return userDAO.countMembers();
    }
}