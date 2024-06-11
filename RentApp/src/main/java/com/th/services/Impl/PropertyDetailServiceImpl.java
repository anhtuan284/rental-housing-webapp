/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Post;
import com.th.pojo.PropertyDetail;
import com.th.repositories.PropertyDetailRepository;
import com.th.services.PropertyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author voquochuy
 */
@Service
@Transactional
public class PropertyDetailServiceImpl implements PropertyDetailService {
    @Autowired
    private PropertyDetailRepository propRepo;
     
    @Override
    @Transactional
    public void savePropOfPost(Post post, PropertyDetail prop) {
         propRepo.savePropOfPost(post, prop);
    }
    
}
