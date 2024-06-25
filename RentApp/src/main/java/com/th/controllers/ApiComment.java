/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.Comment;
import com.th.pojo.Post;
import com.th.pojo.User;
import com.th.services.CommentService;
import com.th.services.PostService;
import com.th.services.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.json.JSONObject;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiComment {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostService postSe;

    @Autowired
    private CommentService commentService;

    private JSONObject callExternalSentimentAnalysisAPI(String content) throws IOException {
        JSONObject jsonInput = new JSONObject();
        jsonInput.put("comment", content);

        String apiUrl = "https://clinic-flask.onrender.com/predict_comment";

        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        } else {
            throw new IOException("Failed to call external API, response code: " + responseCode);
        }
    }

    @PostMapping("/comment/add")
    @Transactional
    public ResponseEntity<String> addComment(@RequestBody Map<String, String> params) {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            int postId = Integer.parseInt(params.get("postId"));
            String content = params.get("content");

            if (content == null || content.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content cannot be empty");
            }

            Post post = postService.getPostById(postId);
            if (post == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid postId");
            }

            JSONObject sentimentResponse = callExternalSentimentAnalysisAPI(content);
            String sentiment = sentimentResponse.optString("sentiment");

            Comment newComment = new Comment();
            newComment.setPostId(post);
            newComment.setUserId(currentUser);
            newComment.setContent(content);

            if ("negative".equals(sentiment) || sentiment.isEmpty()) {
                newComment.setPositive((short) 0); // nagative cmt
            } else if ("positive".equals(sentiment)) {
                newComment.setPositive((short) 1); // positive cmt
            }

            // Add comment
            String result = commentService.addComment(newComment);
            if ("ok".equals(result)) {
                return ResponseEntity.ok("Comment added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment: " + result);
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calling external API: " + e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid postId format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding comment: " + e.getMessage());
        }
    }

    @PostMapping("/comment/update")
    @Transactional
    public ResponseEntity<String> updateComment(@RequestBody Map<String, String> params) {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            int commentId = Integer.parseInt(params.get("commentId"));
            String content = params.get("content");

            if (content == null || content.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content cannot be empty");
            }

            Comment oldComment = commentService.getCommentById(commentId);
            if (oldComment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }

            if (!oldComment.getUserId().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this comment");
            }

            JSONObject sentimentResponse = callExternalSentimentAnalysisAPI(content);
            String sentiment = sentimentResponse.optString("sentiment");

            if ("negative".equals(sentiment) || sentiment.isEmpty()) {
                oldComment.setPositive((short) 0);
            } else if ("positive".equals(sentiment)) {
                oldComment.setPositive((short) 1);
            }
            oldComment.setContent(content);

            String result = commentService.updateComment(oldComment);
            if ("ok".equals(result)) {
                return ResponseEntity.ok("Comment updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update comment: " + result);
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calling external API: " + e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid commentId format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating comment: " + e.getMessage());
        }
    }

    @DeleteMapping("/comment/{commentId}/delete")
    @Transactional
    public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }


            Comment commentDelete = commentService.getCommentById(commentId);
            if (commentDelete == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }

            if (!commentDelete.getUserId().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this comment");
            }

            String result = commentService.deleteComment(commentDelete);
            if ("ok".equals(result)) {
                return ResponseEntity.ok("Comment deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete comment: " + result);
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid commentId format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment: " + e.getMessage());
        }
    }

    @GetMapping("/comment/{postId}")
    @Transactional
    public ResponseEntity<?> GetCmtByPostId(@PathVariable int postId) {
//        if (!params.containsKey("postId")) {
//            return new ResponseEntity<>("Missing required parameter 'postId'", HttpStatus.BAD_REQUEST);
//        }
//
//        Integer postId = params.get("postId");
//        if (postId == null) {
//            return new ResponseEntity<>("Invalid 'postId' parameter. It must be an integer.", HttpStatus.BAD_REQUEST);
//        }
//
//        System.out.println(postId);
//        System.out.println(postId);

        return new ResponseEntity<>(this.commentService.getCmtByPostId(postId), HttpStatus.OK);
    }

}
