/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.th.repositories;

import com.th.pojo.User;

import java.util.List;
import java.util.Map;

/**
 *
 * @author voquochuy
 */
public interface UserRepository {
    
    User getUserByUsername(String username);

    User getUserById(int userId);

    User getUserProfile(int userId);

    void addUser(User user);

    void addOrUpdate(User p);

    List<User> getAllUser(Map<String, String> params);

    boolean authUser(String username, String password);
    
    List<Integer> getListIdFollower (User user);

    int countUserByParam(Map<String, String> params);
    
    User getUserByEmail(String userEmail);
}
