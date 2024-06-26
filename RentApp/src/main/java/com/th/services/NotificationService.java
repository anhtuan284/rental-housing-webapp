/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.th.pojo.Notification;
import com.th.pojo.Post;
import com.th.pojo.User;
import java.util.List;

/**
 *
 * @author voquochuy
 */
public interface NotificationService {

    void addNotification(int postId);

    List<Notification> listNoti(int userId) throws IllegalArgumentException;

    void addNotificationDelele(User userId);

}
