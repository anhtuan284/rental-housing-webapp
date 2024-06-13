/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Image;
import com.th.pojo.Post;
import com.th.repositories.ImageRepository;
import com.th.services.CloudinaryService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author voquochuy
 */
@Repository
@Transactional
public class ImageRepositoryImpl implements ImageRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public void saveListImageOfPost(Post post, MultipartFile[] files) {
        try {
            System.out.println("kkkkkk");
            Session session = factory.getObject().getCurrentSession();
            List<Map<String, Object>> uploadedFiles = cloudinaryService.uploadFiles(files);

            for (Map<String, Object> uploadedFile : uploadedFiles) {
                Image image = new Image();
                image.setUrl((String) uploadedFile.get("url"));
                image.setPostId(post);
//                image.setCreatedDate(new Date());
                session.save(image);
            }
            session.flush();
        } catch (IOException ex) {
            Logger.getLogger(ImageRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
