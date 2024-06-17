/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import com.th.pojo.Comment;

/**
 *
 * @author voquochuy
 */
public interface CommentRepository {

    void saveOrUpdate(Comment comment);

    Comment getCommentById(int id);

    void delete(Comment cmt);

}
