/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.th.pojo.Comment;

/**
 *
 * @author voquochuy
 */
public interface CommentService {
    String addComment(Comment comment);
    String updateComment(Comment comment);
    Comment getCommentById(int id);

    String deleteComment(Comment commentDelete);
    
}
