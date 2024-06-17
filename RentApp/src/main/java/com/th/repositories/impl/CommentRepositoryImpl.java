/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Comment;
import com.th.repositories.CommentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author voquochuy
 */
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    @Transactional
    public void saveOrUpdate(Comment comment) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.saveOrUpdate(comment);
    }

    @Override
    @Transactional
    public Comment getCommentById(int id) {
        Session session = factoryBean.getObject().getCurrentSession();
        return session.get(Comment.class, id);
    }

    @Override
    public void delete(Comment cmt) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        if (cmt != null) {
            s.delete(cmt);
        }
    }
}

