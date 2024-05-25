/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Role;
import com.th.repositories.RoleRepository;
import com.th.services.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author voquochuy
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired 
    private RoleRepository roleRepo;
    
  
    @Override
    public String getUserRoleName(int userRoleId) {
    return  roleRepo.getUserRoleName(userRoleId);
    
    }
    
}
