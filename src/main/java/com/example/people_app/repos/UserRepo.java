package com.example.people_app.repos;

import com.example.people_app.models.User;

import java.util.List;

public interface UserRepo {
    void save(User user);
    User findUser();
    List<User> findAll();
    boolean isExist(String email, String password);
}
