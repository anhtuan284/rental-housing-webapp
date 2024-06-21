/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.th.pojo.Post;
import java.util.List;
import java.util.Map;

/**
 *
 * @author voquochuy
 */
public interface PostService {
    List<Post> getPosts(int typeId,boolean status,boolean actived, Map<String, String> params);
    void addOrUpdate(Post post);
    Post getPostById(int id);
    void deletePost(int id);
    void approvePost(int postId);
    List<Post> getPostOfRenter(Map<String, String> param);
    List<Post> getPostOfLandlord(Map<String, String> param);
    Post getPostDetail(int postId);
    void unActivedPost(int postId);

}
