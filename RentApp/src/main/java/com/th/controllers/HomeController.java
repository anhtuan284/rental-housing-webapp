/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.Post;
import com.th.services.PostService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Peter
 */
@Controller
public class HomeController {
    @Autowired
    private PostService postSe;
    
    @RequestMapping("/")
    public String getListPendingLesaePost(@RequestParam(required = false) Map<String, String> params,Model model) {
        List<Post> posts = postSe.getPosts(1, false, params);
        // In ra thông tin của mỗi bài đăng
        model.addAttribute("posts", posts);
        return "index";
    }
  

}

 

