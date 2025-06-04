package com.example.controller;

import com.example.service.UserService;
import com.example.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("register")
    public String register(User u) {
        if(userService.register(u)){
            return "redirect:index.jsp";
        }else{
            return "error1";
        }
    }

    @RequestMapping("login")
    public String login(User u, HttpServletRequest request) {
        if(userService.login(u,request)){
            return "redirect:space.jsp";
        }else{
            return "error1";
        }
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        userService.logout(request);
        return "index";
    }
}
