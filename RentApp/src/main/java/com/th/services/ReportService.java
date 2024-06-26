/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.services;

import com.th.pojo.ReportPost;

/**
 *
 * @author voquochuy
 */
public interface ReportService {
    void unReportForPostById(int postId);

    String addReport(ReportPost report);
    
}
