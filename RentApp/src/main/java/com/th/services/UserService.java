/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.th.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author voquochuy
 */
public interface UserService extends UserDetailsService {
    User getUserByUsername (String username);
    
}
    