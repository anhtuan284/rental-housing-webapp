/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Notification;
import com.th.pojo.Post;
import com.th.pojo.User;
import com.th.repositories.NotificationRepository;
import com.th.services.NotificationService;
import com.th.services.PostService;
import com.th.services.UserService;
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
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notiRepo;
    @Autowired
    private PostService postSe;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void addNotification(int postId) {
        Post post = postSe.getPostById(postId);
        notiRepo.addNotification(post.getUserId(), post);
    }

    @Override
    public List<Notification> listNoti(int userId) throws IllegalArgumentException {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
        try {
            return notiRepo.listNoti(userId);
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch notifications", e);
        }
    }

}
