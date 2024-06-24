/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.Notification;
import com.th.services.NotificationService;
import com.th.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author voquochuy
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiNotificationController {

    @Autowired
    private NotificationService notiSe;
    
    @Autowired
    private UserService userService;

    @GetMapping("/Notification/getNotificationOfUser")
    public ResponseEntity<List<Notification>> getNotificationOfUser() {
        return new ResponseEntity<>(this.notiSe.listNoti(userService.getCurrentUser().getId()), HttpStatus.OK);
    }

}
