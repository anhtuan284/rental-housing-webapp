/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.Post;
import com.th.pojo.ReportPost;
import com.th.pojo.User;
import com.th.services.PostService;
import com.th.services.ReportService;
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
public class ApiReportPostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ReportService ReportPostSe;

    @PostMapping("/report/add")
    @Transactional  
    public ResponseEntity<String> addReport(@RequestBody Map<String, String> params) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        int postId = Integer.parseInt(params.get("postId"));
        String reason = params.get("reason");
        if (reason == null || reason.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content cannot be empty");
        }

        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid postId");
        }
        ReportPost report = new ReportPost();
        report.setPostId(post);
        report.setReason(reason);
        report.setReporterId(currentUser);
        String result = ReportPostSe.addReport(report);
        if ("ok".equals(result)) {
            return ResponseEntity.ok("Report added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment: " + result);
        }
    }

}
