/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Typeofpost;
import com.th.repositories.TypeOfPostRepository;
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
public class TypeOfPostRepositoryImpl implements TypeOfPostRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Typeofpost getTypeById(int typeId) {
        Session session = this.factory.getObject().getCurrentSession();
        TypedQuery<Typeofpost> query = session.createQuery("SELECT t FROM Typeofpost t WHERE t.typeId = :typeId", Typeofpost.class);
        query.setParameter("typeId", typeId);
        return query.getSingleResult();
    }
}
