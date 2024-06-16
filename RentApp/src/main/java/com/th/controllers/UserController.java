package com.th.controllers;

import com.th.pojo.User;
import com.th.services.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@PropertySource("classpath:configs.properties")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    @GetMapping("/user/all")
    public String getAllUser(@RequestParam(required = false)Map<String, String> params, Model model) {
        List<User> users = userService.getAllUsers(params);
        model.addAttribute("users", users);

        int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
        int totalUsers = userService.countUserByParams(params);
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        int currentPage = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("params", params);

        return "userList";
    }

    @GetMapping("/user")
    public String createView(Model model) {
        model.addAttribute("user", new User());
        return "userDetails";
    }

    @RequestMapping("/user/{userId}")
    public String getUserDetail(Model model, @PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "userDetails";
        }
        return "notFound";
    }

    @PostMapping("/user")
    public String createOrUpdate(@ModelAttribute(value = "user") @Valid User p,
                                BindingResult rs) {
        if (!rs.hasErrors()) {
            try {
                this.userService.addOrUpdate(p);
                return "redirect:/user/all";
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return "userDetails";
    }

    @RequestMapping("/user/{userId}/disable")
    public String deleteUser(@PathVariable Integer userId) {
        try {
            User u = this.userService.getUserById(userId);
            System.out.println(u);
            u.setActivated((short) 0);
            System.out.println(u.getActivated());
            this.userService.addOrUpdate(u);
            return "redirect:/user/all";
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return "redirect:/";
        }
    }


}
