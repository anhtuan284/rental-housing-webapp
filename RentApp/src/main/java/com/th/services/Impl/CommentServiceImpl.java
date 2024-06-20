/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Comment;
import com.th.pojo.Post;
import com.th.repositories.CommentRepository;
import com.th.services.CommentService;
import com.th.services.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author voquochuy
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostService postSe;
    @Autowired
    private CommentRepository cmtRepo;

    @Override
    @Transactional
    public String addComment(Comment comment) {
        try {
            if (comment == null || comment.getPostId() == null || comment.getUserId() == null || comment.getContent() == null || comment.getContent().isEmpty()) {
                return "Invalid comment data";
            }

            Post post = postSe.getPostById(comment.getPostId().getPostId());
            if (post == null) {
                return "Post not found";
            }

            cmtRepo.saveOrUpdate(comment);

            return "ok";
        } catch (Exception e) {
            return "Failed to add comment: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String updateComment(Comment comment) {
        try {
            if (comment == null || comment.getPostId() == null || comment.getUserId() == null || comment.getContent() == null || comment.getContent().isEmpty()) {
                return "Invalid comment data";
            }

            Post post = postSe.getPostById(comment.getPostId().getPostId());
            if (post == null) {
                return "Post not found";
            }
            Comment cmt = cmtRepo.getCommentById(comment.getCommentId());
            if (cmt == null) {
                return "comment not found";
            }
            cmtRepo.saveOrUpdate(comment);

            return "ok";
        } catch (Exception e) {
            return "Failed to add comment: " + e.getMessage();
        }
    }

    @Override
    public Comment getCommentById(int id) {
        return cmtRepo.getCommentById(id);
    }

    @Override
    @Transactional
    public String deleteComment(Comment commentDelete) {
        try {
            if (commentDelete == null) {
                return "Invalid comment data";
            }

            Comment cmt = cmtRepo.getCommentById(commentDelete.getCommentId());
            if (cmt == null) {
                return "Comment not found";
            }

            cmtRepo.delete(cmt);

            return "ok";
        } catch (Exception e) {
            return "Failed to delete comment: " + e.getMessage();
        }
    }

    @Override
    public List<Comment> getCmtByPostId(int postId) {
        return cmtRepo.getCmtByPostId(postId);
    }
}
