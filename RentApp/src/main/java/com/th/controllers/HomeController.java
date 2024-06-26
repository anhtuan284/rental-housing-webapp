/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.Image;
import com.th.pojo.Post;
import com.th.pojo.PropertyDetail;
import com.th.services.*;


import com.th.pojo.User;
import com.th.services.PostService;
import java.security.Principal;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peter
 */
@Controller
@ControllerAdvice
@Transactional
public class HomeController {

    @Autowired
    private PostService postSe;

    @Autowired
    private TypeOfPostService typeSe;

    @Autowired
    private UserService userSe;

    @Autowired
    private CloudinaryService cloudinaryService;
    
    @Autowired
    private ImageService imgService;

    @Autowired
    private RoleService roleSe;

    @RequestMapping("/")
    public String home(@RequestParam(required = false) Map<String, String> params, Model model) {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/post/all")
    public String getListPendingLesaePost(@RequestParam(required = false) Map<String, String> params, Model model) {
        List<Post> posts = postSe.getPosts(1, false,true, params);
        model.addAttribute("posts", posts);
        return "postlist";
    }
    @GetMapping("/post/reportedPosts")
    public String getListreportedPosts(@RequestParam(required = false) Map<String, String> params, Model model) {
        List<Post> posts = postSe.getListreportedPosts(true,true, params);
        model.addAttribute("posts", posts);
        return "reportedPosts";
    }

    @GetMapping("/post/add")
    public String creatPostView(@RequestParam(required = false) Map<String, String> params, Model model) {
        model.addAttribute("post", new Post());
        return "createPost";
    }

    @PostMapping("post/add")
    @Transactional
    public String createProduct(Principal pri, @ModelAttribute(value = "post") @Valid Post p,
            BindingResult rs, @RequestParam("files") MultipartFile[] files) {
        if (!rs.hasErrors()) {
            try {
                User u = userSe.getUserByUsername(pri.getName());
                p.setUserId(u);
                p.setStatus(false);
                p.setTypeId(typeSe.getTypeById(2));
                p.setStatus(true);
                p.setActived(true);
                this.postSe.addOrUpdate(p);
                imgService.saveListImageOfPost(p, files);
                return "redirect:/";
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
        return "createPost";
    }

    @GetMapping("/post/{id}")
    public String getPostDetail(Model model, @PathVariable(value = "id") int id) {
        Post p = postSe.getPostById(id);
        model.addAttribute("post", p);
        return "detail";
    }

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("roles", this.roleSe.getRoles());
    }


}
