/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.Image;
import com.th.pojo.Post;
import com.th.pojo.PropertyDetail;
import com.th.services.PostService;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Peter
 */
@Controller
@Transactional
public class HomeController {
    @Autowired
    private PostService postSe;

    @RequestMapping("/")
    public String home(@RequestParam(required = false) Map<String, String> params, Model model) {
        return "index";
    }

    @GetMapping("/post/all")
    public String getListPendingLesaePost(@RequestParam(required = false) Map<String, String> params, Model model) {
        List<Post> posts = postSe.getPosts(1, false, params);
        model.addAttribute("posts", posts);
        return "postlist";
    }

    @GetMapping("/post/{id}")
    public String getPostDetail(Model model, @PathVariable(value = "id") int id) {
        Post p = postSe.getPostById(id);
        model.addAttribute("post", p);
        return "detail";
    }


    @GetMapping("/stats")
    public String stats() {
        return "stats";
    }


}

