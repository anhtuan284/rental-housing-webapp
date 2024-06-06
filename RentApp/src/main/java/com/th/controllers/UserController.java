package com.th.controllers;

import com.th.pojo.User;
import com.th.services.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public String getAllUser(@RequestParam(required = false)Map<String, String> params, Model model) {
        List<User> users = userService.getAllUsers(params);
        model.addAttribute("users", users);
        return "userList";
    }

    @RequestMapping("/{userId}")
    public String getUserDetail(Model model, @PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "userDetails";
        }
        return "notFound";
    }
}
