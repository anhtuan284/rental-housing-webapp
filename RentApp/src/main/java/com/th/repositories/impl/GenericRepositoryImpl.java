package com.th.repositories.impl;

import com.th.repositories.GenericRepository;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

public class GenericRepositoryImpl<T> implements GenericRepository<T>{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Autowired
    private Environment env;

    private Class<T> entityClass;

    public GenericRepositoryImpl(Class<T> entityType) {
        this.entityClass = entityType;
    }
    
    @Override
    public List<T> all() {
        Session s = this.factory.getObject().getCurrentSession();
        List<T> list = s.createQuery("from " + entityClass.getName()).list();
        s.close();
        return list;
    }

    @Override
    public T get(Serializable id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(entityClass, id);
    }

    @Override
    public void addOrUpdate(T entity) {
        Session s = this.factory.getObject().getCurrentSession();
        Transaction t = s.beginTransaction();
        try {
            s.saveOrUpdate(entity);
        } catch (Exception e) {
            t.rollback();
            System.out.println(e);
        } finally {
            s.close();
        }
    }

    @Override
    public void delete(Serializable id) {
        
    }
}
