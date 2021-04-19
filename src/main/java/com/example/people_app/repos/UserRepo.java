package com.example.people_app.repos;

import com.example.people_app.models.User;

import java.util.List;

public interface UserRepo {
    boolean save(User user);
    User findUser(int id);
    List<User> findAll();
    boolean isExist(String email, String passwordHash);
    boolean isExist(String email);
}
