/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Hi
 */
public interface GenericRepository<T> {
    List<T> all();
    T get(Serializable id);
    void addOrUpdate(T post);
    void delete(Serializable id);
}
