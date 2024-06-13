/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import com.th.pojo.Post;
import com.th.pojo.User;

/**
 *
 * @author voquochuy
 */
public interface NotificationRepository {
    void addNotification(User user,Post post);
    
}
