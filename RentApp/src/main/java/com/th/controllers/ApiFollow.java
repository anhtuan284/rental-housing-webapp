/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.User;
import com.th.services.FollowService;
import com.th.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author voquochuy
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiFollow {

    @Autowired
    private FollowService followSe;
    @Autowired
    private UserService userRepo;

    @PostMapping("/Follow")
    @Transactional
    public ResponseEntity<String> Follow(@RequestBody Map<String, Integer> params) {
        try {
            int followerId = params.get("follower");
            int followeeId = params.get("followee");
            if (followerId == followeeId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail: invalid input");
            }
            String result = followSe.updateFollow(followerId, followeeId);

            if ("ok".equals(result)) {
                return ResponseEntity.ok("Follow updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update follow");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    @PostMapping("/UnFollow")
    @Transactional
    public ResponseEntity<String> UnFollow(@RequestBody Map<String, Integer> params) {
        try {
            int followerId = params.get("follower");
            int followeeId = params.get("followee");
            User uFollower = userRepo.getUserById(followerId);
            User uFollowee = userRepo.getUserById(followeeId);
            if (followerId == followeeId) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail: invalid input");
            }
            String result = followSe.unFollow(followerId, followeeId);

            if ("ok".equals(result)) {
                return ResponseEntity.ok("Follow updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update follow");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }
}
