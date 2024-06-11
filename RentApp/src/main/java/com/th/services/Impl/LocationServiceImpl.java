/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Location;
import com.th.pojo.Post;
import com.th.repositories.LocationRepository;
import com.th.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author voquochuy
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{
    @Autowired
    private LocationRepository locantionRepo;
    
    @Override
    @Transactional
    public void saveLocationOfProp(Post post, Location location) {
        System.out.println("cc");
        locantionRepo.saveLocationOfProp(post, location);
    }
    
}
