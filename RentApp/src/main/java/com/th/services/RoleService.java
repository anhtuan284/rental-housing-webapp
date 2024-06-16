/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.th.pojo.Role;
import java.util.List;

/**
 *
 * @author voquochuy
 */
public interface RoleService {
    String  getUserRoleName(int userRoleId);

    List<Role> getRoles();
}
