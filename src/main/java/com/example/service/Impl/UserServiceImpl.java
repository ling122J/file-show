package com.example.service.Impl;

import com.example.dao.IUser;
import com.example.service.UserService;
import com.example.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    IUser userDAO;

    public boolean register(User u) {
        if (u.getUsername() == null || u.getPassword() == null
                || u.getUsername().isEmpty() || u.getPassword().isEmpty()) {
            return false;
        }
        User user = userDAO.selByName(u.getUsername());
        if (user == null) {//未在数据库找到该用户
            userDAO.adduser(u);
        }
        return true;
    }

    public boolean login(User u, HttpServletRequest request) {
        if (u.getUsername() == null || u.getPassword() == null
                || u.getUsername().isEmpty() || u.getPassword().isEmpty()) {
            return false;
        }
        User user = userDAO.selByName_pwd(u.getUsername(), u.getPassword());
        if (user == null) { //未找到该用户,登录失败
            return false;
        } else { //登录成功
            // 将用户信息存储到 session 中
            request.getSession().setAttribute("user", user);
        }
        return true;
    }

    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
