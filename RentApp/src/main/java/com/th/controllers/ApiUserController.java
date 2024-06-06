package com.th.controllers;

import java.util.Map;

import com.th.pojo.Role;
import com.th.pojo.User;
import com.th.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/users/", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam Map<String, String> params, @RequestPart MultipartFile[] files) {
        User user = new User();
        user.setName(params.get("name"));
        user.setUsername(params.get("username"));
        user.setPassword(params.get("password"));
        user.setNumberPhone(params.get("phone"));
        user.setEmail(params.get("email"));
        user.setCccd(params.get("cccd"));
        user.setCccd(params.get("address"));

        if (files.length > 0)
            user.setFile(files[0]);

        this.userService.addUser(user);
    }
}