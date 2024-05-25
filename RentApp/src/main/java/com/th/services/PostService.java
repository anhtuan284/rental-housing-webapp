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
   List<Post> getPosts(Map<String, String> params);
    void addOrUpdate(Post post);
    Post getPostById(int id);
    void deletePost(int id);
}
