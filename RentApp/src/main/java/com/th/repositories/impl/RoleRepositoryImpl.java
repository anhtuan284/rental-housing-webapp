/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.repositories.RoleRepository;
import javax.persistence.TypedQuery;
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
public class RoleRepositoryImpl implements RoleRepository {

     @Autowired
    private LocalSessionFactoryBean factory;

   @Override
public String getUserRoleName(int userRoleId) {
    Session session = this.factory.getObject().getCurrentSession();
    TypedQuery<String> query = session.createQuery(
            "SELECT r.name FROM Role r  WHERE r.id = :userRoleId", String.class);
    query.setParameter("userRoleId", userRoleId);
    return query.getSingleResult();
}
}
