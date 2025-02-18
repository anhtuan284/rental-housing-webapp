/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.th.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 *
 * @author voquochuy
 */
public interface UserService extends UserDetailsService {

    User getUserByUsername(String username);

    List<User> getAllUsers(Map<String, String> params);

    User getUserById(Integer id);

    void addUser(User user);

    User getUserProfile(Integer id);

    void addOrUpdate(User user);

    void mergeGgAcc(User user);

    boolean authUser(String username, String password);

    List<User> getListUserFollower(User user);

    User getCurrentUser();

    int countUserByParams(Map<String, String> params);

    User getUserByEmail(String userEmail);

}
