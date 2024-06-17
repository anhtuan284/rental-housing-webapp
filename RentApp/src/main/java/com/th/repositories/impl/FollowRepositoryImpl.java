/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Follow;
import com.th.pojo.User;
import com.th.repositories.FollowRepository;
import com.th.repositories.UserRepository;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author voquochuy
 */
@Repository
public class FollowRepositoryImpl implements FollowRepository {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    public String updateFollow(int followerID, int followeeID) {
        if (getFollowByTwoId(followerID, followeeID) == null) {
            Session session = factoryBean.getObject().getCurrentSession();
            User uFollower = userRepo.getUserById(followerID);
            User uFollowee = userRepo.getUserById(followeeID);
            if (uFollower == null || uFollowee == null) {
                return "fail";
            }
            Follow follow = new Follow();
            follow.setFolloweeId(uFollowee);
            follow.setFollowerId(uFollower);
            session.save(follow);
            return "ok";
        } else {
            return "fail";
        }
    }

    @Override
    public Follow getFollowByTwoId(int followerID, int followeeID) {
        Session session = factoryBean.getObject().getCurrentSession();
        String hql = "SELECT f FROM Follow f WHERE f.followerId.id = :followerId AND f.followeeId.id = :followeeId";
        Query<Follow> query = session.createQuery(hql, Follow.class);
        query.setParameter("followerId", followerID);
        query.setParameter("followeeId", followeeID);
        Follow follow = null;
        try {
            follow = query.getSingleResult();
        } catch (NoResultException e) {
        }
        return follow;
    }

    @Override
    public String unFollow(int followerID, int followeeID) {
        Session session = factoryBean.getObject().getCurrentSession();

        Follow follow = getFollowByTwoId(followerID, followeeID);
        if (follow != null) {
            User uFollower = userRepo.getUserById(followerID);
            User uFollowee = userRepo.getUserById(followeeID);
            System.out.println(follow);
            if (uFollower == null || uFollowee == null) {
                return "fail";
            }
            session.delete(follow);
            return "ok";
        } else {
            return "fail";
        }
    }

}
