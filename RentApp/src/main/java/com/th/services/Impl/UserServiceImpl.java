/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.th.pojo.User;
import com.th.repositories.UserRepository;
import com.th.services.RoleService;
import com.th.services.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author voquochuy
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.userRepo.getUserByUsername(username);

        if (u == null) {
            throw new UsernameNotFoundException("Không tồn tại!");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        String userRole = this.roleService.getUserRoleName(u.getRoleId().getId());
        authorities.add(new SimpleGrantedAuthority(userRole));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "statsUsersByPeriod"),
//            @CacheEvict(value = "statsUserByRole"),
//            @CacheEvict(value = "countCreatedUser")
//    })
    public void addUser(User user) {
        cacheManager.getCache("statsUsersByPeriod").clear();
        cacheManager.getCache("statsUserByRole").clear();
        cacheManager.getCache("countCreatedUser").clear();
        user.setPassword(passEncoder.encode(user.getPassword()).toString());
        if (!user.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.userRepo.addUser(user);
    }

    @Override
//    @Cacheable(value = "users")
    public List<User> getAllUsers(Map<String, String> params) {
        List<User> users = userRepo.getAllUser(params);
        return users;
    }

    @Override
//    @Cacheable(value = "users", key = "#id")
    public User getUserById(Integer id) {
        User u = this.userRepo.getUserById(id);
        return u;
    }

    @Override
    public User getUserProfile(Integer id) {
        User u = this.userRepo.getUserProfile(id);
        return u;
    }

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "statsUsersByPeriod"),
//            @CacheEvict(value = "statsUserByRole"),
//            @CacheEvict(value = "countCreatedUser")
//    })
    public void addOrUpdate(User user) {
        cacheManager.getCache("statsUsersByPeriod").clear();
        cacheManager.getCache("statsUserByRole").clear();
        cacheManager.getCache("countCreatedUser").clear();
        if (user.getFile() != null && !user.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.userRepo.addOrUpdate(user);
    }
    


    @Override
    public boolean authUser(String username, String password) {
        return this.userRepo.authUser(username, password);
    }

    @Override
    public List<User> getListUserFollower(User user) {
        return userRepo.getListUserFollower(user);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return getUserByUsername(username);
        }
        return null;
    }

    @Override
    public int countUserByParams(Map<String, String> params) {
        return userRepo.countUserByParam(params);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return userRepo.getUserByEmail(userEmail);
    }

    @Override
    public void mergeGgAcc(User user) {
        this.userRepo.addOrUpdate(user);
    }
}
