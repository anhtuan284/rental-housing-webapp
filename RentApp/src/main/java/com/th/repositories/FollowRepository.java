/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import com.th.pojo.Follow;

/**
 *
 * @author voquochuy
 */
public interface FollowRepository {

    String updateFollow(int followerID, int followeeID);

    String unFollow(int followerID, int followeeID);

    Follow getFollowByTwoId(int followerID, int followeeID);

}
