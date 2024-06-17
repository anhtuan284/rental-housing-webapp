/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.repositories.FollowRepository;
import com.th.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author voquochuy
 */
@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepo;

    @Override
    @Transactional
    public String updateFollow(int followerID, int followeeID) {
        try {
            return followRepo.updateFollow(followerID, followeeID);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String unFollow(int followerId, int followeeId) {
        try {
            return followRepo.unFollow(followerId, followeeId);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public boolean checkFollowing(Integer currentUser, Integer userIdToCheck) {
        if (followRepo.getFollowByTwoId(currentUser, userIdToCheck) != null) {
                        System.out.println("44444");
            return true;
        } else {
            System.out.println("1212112");
            return false;
        }
    }
}
