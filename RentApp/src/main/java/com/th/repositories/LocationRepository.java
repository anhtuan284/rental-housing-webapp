/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import com.th.pojo.Location;
import com.th.pojo.Post;

/**
 *
 * @author voquochuy
 */
public interface LocationRepository {

    void saveLocationOfProp(Post post, Location location);
    
}
