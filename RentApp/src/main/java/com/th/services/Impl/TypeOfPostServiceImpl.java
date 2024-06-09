/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Typeofpost;
import com.th.repositories.TypeOfPostRepository;
import com.th.services.TypeOfPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author voquochuy
 */
@Service
public class TypeOfPostServiceImpl implements TypeOfPostService{
    @Autowired 
    private TypeOfPostRepository typeRepo;

    @Override
    public Typeofpost getTypeById(int typeId) {
    return  typeRepo.getTypeById(typeId);
    }
    
}

