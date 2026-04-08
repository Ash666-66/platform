package com.example.secondhand.service;

import com.example.secondhand.entity.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
    User findByUsername(String username);
    User findByEmail(String email);
}