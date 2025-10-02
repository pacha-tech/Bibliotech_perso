package com.tp.dao.interfaces;


import com.tp.model.User;

import java.util.List;

public interface UserDAO {
      boolean addUser(User user) ;
      boolean updateUser(User user) ;
      boolean deleteUser(String userId) ;
      User findById(String userId) ;
      List<User> findByname(String name);
      List<User> getAllMembers();
      int countMembers();
}
