package com.th.controllers;

import com.th.pojo.Post;
import com.th.services.NotificationService;
import com.th.services.PostService;
import com.th.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

//    @GetMapping
//    public List<Post> getListLesaePost(@RequestParam(required = false) Map<String, String> params) {
//        int typeId = 2;
//        boolean status = true;
//        return postSe.getPosts(typeId, status, params);
//    }

    @GetMapping("/admin/post/approve/{postId}")
    public ResponseEntity<String> approvePost(@PathVariable(value = "postId") Integer postId) {
        try {
            postSe.approvePost(postId);
            notiSe.addNotification(postId);

            return ResponseEntity.ok("Post approved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to approve post: " + e.getMessage());
        }
    }
}
