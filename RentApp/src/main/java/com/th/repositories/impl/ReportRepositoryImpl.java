/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.ReportPost;
import com.th.repositories.ReportRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
public class ReportRepositoryImpl implements ReportRepository {

    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    public void unReportForPostById(int postId) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        String hql = "DELETE FROM ReportPost rp WHERE rp.postId.postId = :postId";
        Query query = session.createQuery(hql);
        query.setParameter("postId", postId);
        query.executeUpdate();
    }

    @Override
    public List<ReportPost> getListReportById(int postId) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ReportPost> criteriaQuery = builder.createQuery(ReportPost.class);
        Root<ReportPost> root = criteriaQuery.from(ReportPost.class);

        Predicate predicate = builder.equal(root.get("postId").get("postId"), postId);
        criteriaQuery.where(predicate);

        Query<ReportPost> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void add(ReportPost report) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(report);
    }
}
