package com.example.service;

import com.example.vo.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    public boolean register(User u);

    public boolean login(User u, HttpServletRequest request);
    public void logout(HttpServletRequest request);

}
