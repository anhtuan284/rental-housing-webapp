/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author atuan
 */
public interface GenericService<T> {
    public List<T> findAll() throws Exception;
    public void saveOrUpdate(T t) throws Exception;
    public void delete(Serializable id) throws Exception;
    public T find(Serializable id) throws Exception;    
}
