/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;
   import com.th.pojo.Post;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author voquochuy
 */
public interface ImageService {
    void saveListImageOfPost(Post post, MultipartFile[] files);
    
}