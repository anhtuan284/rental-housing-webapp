/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import com.th.pojo.Role;
import java.util.List;

/**
 *
 * @author voquochuy
 */
public interface RoleRepository {
    String  getUserRoleName(int userRoleId);

    Role getRoleById(int roleId);

    List<Role> getRoles();
}
