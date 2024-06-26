package com.th.controllers;

import com.th.pojo.Post;
import com.th.pojo.User;
import com.th.services.NotificationService;
import com.th.services.PostService;
import com.th.services.ReportService;
import com.th.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PostController {

    @Autowired
    private PostService postSe;
    @Autowired
    private NotificationService notiSe;
    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportSe;

    @PostMapping("/admin/post/approve/{postId}")
    public ResponseEntity<String> approvePost(@PathVariable(value = "postId") Integer postId) {
            postSe.approvePost(postId);
            notiSe.addNotification(postId);

            return ResponseEntity.ok("Post approved successfully.");
       
    }

    @PostMapping("/admin/post/reject/{postId}")
    public ResponseEntity<String> reject(@PathVariable(value = "postId") Integer postId) {
        try {
            postSe.unActivedPost(postId);
            notiSe.addNotification(postId);

            return ResponseEntity.ok("Post reject successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to approve post: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/post/delete/{postId}")
    public ResponseEntity<String> delete(@PathVariable(value = "postId") Integer postId) {
        try {
            Post post = postSe.getPostById(postId);
            postSe.deletePost(postId);
            notiSe.addNotificationDelele(post.getUserId());
            return ResponseEntity.ok("Post delete successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to delete post: " + e.getMessage());
        }
    }

    @PostMapping("/admin/post/unrported/{postId}")
    public ResponseEntity<String> unrported(@PathVariable(value = "postId") Integer postId) {
        try {
            reportSe.unReportForPostById(postId);
            return ResponseEntity.ok("Post unrported successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to delete post: " + e.getMessage());
        }
    }

}
