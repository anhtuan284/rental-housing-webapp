/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Post;
import com.th.pojo.PropertyDetail;
import com.th.repositories.PropertyDetailRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author voquochuy
 */
@Repository
@Transactional
public class PropertyDetailRepositoryImpl implements PropertyDetailRepository{
   @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void savePropOfPost(Post post, PropertyDetail prop) {
        Session session = factory.getObject().getCurrentSession();
        prop.setPostId(post.getPostId());
        session.save(prop);
    }
    
}
