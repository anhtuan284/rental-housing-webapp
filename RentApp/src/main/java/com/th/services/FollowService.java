/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

/**
 *
 * @author voquochuy
 */
public interface FollowService {

    String updateFollow(int followerID, int followeeID);

    String unFollow(int followerID, int followeeID);


}
