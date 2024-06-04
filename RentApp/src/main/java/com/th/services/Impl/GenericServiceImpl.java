/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.repositories.GenericRepository;
import com.th.services.GenericService;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author atuan
 */
public abstract class GenericServiceImpl<T, T2 extends GenericRepository<T>> implements GenericService<T>{

    @Autowired
    private T2 repo;
    
    @Override
    public List<T> findAll() throws Exception {
        return this.repo.all();
    }

    @Override
    public void saveOrUpdate(T t) throws Exception {
        this.repo.addOrUpdate(t);
    }

    @Override
    public void delete(Serializable id) throws Exception {
        this.repo.delete(id);
    }

    @Override
    public T find(Serializable id) throws Exception {
        return this.repo.get(id);
    }
    
}
