/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import com.th.pojo.Post;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author voquochuy
 */
public interface PostRepository {

    List<Post> getPosts(int typeId, boolean status,boolean actived, Map<String, String> params);

    void addOrUpdate(Post post);

    Post getPostById(int id);

    void deletePost(int id);

    void approvePost(int postId);

    Post getPostDetail(int postId);
    
    void unActivedPost(int postId);

    List<Post> findNearHouse(BigDecimal userLat, BigDecimal userLon, int dist);
}
