/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Post;
import com.th.repositories.PostRepository;
import com.th.services.PostService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author voquochuy
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepo;

    @Override
    public Post getPostById(int id) {
        return this.postRepo.getPostById(id);
    }

    @Override
    public void addOrUpdate(Post post) {
        postRepo.addOrUpdate(post);
    }

    @Override
    public void deletePost(int id) {
        postRepo.deletePost(id);
    }


    @Override
    public List<Post> getPosts(int typeId, boolean status, Map<String, String> params) {
        return postRepo.getPosts(typeId, status, params);
    }

    @Override
    public void approvePost(int postId) {
        postRepo.approvePost(postId);
    }


}
