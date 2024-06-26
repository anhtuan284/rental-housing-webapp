/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories;

import com.th.pojo.ReportPost;
import java.util.List;

/**
 *
 * @author voquochuy
 */
public interface ReportRepository {

    void unReportForPostById(int postId);
    
    List<ReportPost> getListReportById(int postId);

     void add(ReportPost report);
}
