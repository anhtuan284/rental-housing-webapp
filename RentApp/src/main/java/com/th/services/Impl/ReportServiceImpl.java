/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services.Impl;

import com.th.pojo.Post;
import com.th.pojo.ReportPost;
import com.th.repositories.ReportRepository;
import com.th.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author voquochuy
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepo;

    @Override
    public void unReportForPostById(int postId) {
        reportRepo.unReportForPostById(postId);
    }

    @Override
    @Transactional
    public String addReport(ReportPost report) {
        try {
            reportRepo.add(report);
            return "ok";
        } catch (Exception e) {
            return "Failed to add comment: " + e.getMessage();
        }
    }

}
