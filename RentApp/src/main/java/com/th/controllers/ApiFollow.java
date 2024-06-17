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
    private UserService userSe;

    @PostMapping("/Follow")
    @Transactional
    public ResponseEntity<String> Follow(@RequestBody Map<String, Integer> params) {
        try {
            User currentUser = userSe.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            int followerId = currentUser.getId();
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
            User uFollower = userSe.getCurrentUser();
            if (uFollower == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            int followerId = uFollower.getId();
            int followeeId = params.get("followee");
            User uFollowee = userSe.getUserById(followeeId);
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

    @PostMapping("/CheckFollow")
    public ResponseEntity<Boolean> CheckFollow(@RequestBody Map<String, Integer> params) {
        try {
            User uFollower = userSe.getCurrentUser();
            if (uFollower == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }
            Integer userIdToCheck = params.get("userId");
            boolean isFollowing = followSe.checkFollowing(uFollower.getId(), userIdToCheck);
            //true = đã fl / false = chưa fl
            return ResponseEntity.ok(isFollowing);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

}
