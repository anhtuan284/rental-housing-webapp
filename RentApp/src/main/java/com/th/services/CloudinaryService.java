/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author voquochuy
 */
@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public List<Map<String, Object>> uploadFiles(MultipartFile[] files) throws IOException {
        System.out.println("vvvvvvv");
        List<Map<String, Object>> uploadedFiles = new ArrayList<>();
        System.out.println("fasda");

        for (MultipartFile file : files) {
            System.out.println("vv;;;;;");

            Map<String, Object> uploadedFile = uploadFile(file);
            System.out.println("wqe21;;;;;");

            uploadedFiles.add(uploadedFile);
        }
        System.out.println("ccvvvvvvv");

        return uploadedFiles;
    }

    private Map<String, Object> uploadFile(MultipartFile file) throws IOException {
        System.out.println("qqweqweqw;");

        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }
}
