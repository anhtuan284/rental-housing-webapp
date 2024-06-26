/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.*;
import com.th.repositories.PostRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.core.env.Environment;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
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
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private LocalSessionFactoryBean factoryBean;
    @Autowired
    private Environment env;

    @Override
    public List<Post> getPosts(int typeId, boolean status, boolean actived, Map<String, String> params) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Post> q = b.createQuery(Post.class);
        Root<Post> post = q.from(Post.class);

        Join<Post, PropertyDetail> propJoin = post.join("propertyDetail", JoinType.INNER);
        Join<Post, Location> locationJoin = post.join("location", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(post.get("status"), status));
        predicates.add(b.equal(post.get("actived"), actived));
        predicates.add(b.equal(post.get("typeId").get("typeId"), typeId));

        String userId = params.get("userId");
        if (userId != null && !userId.isEmpty()) {
            predicates.add(b.equal(post.get("userId"), Integer.parseInt(userId)));
        }

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(b.or(b.like(post.get("title"), String.format("%%%s%%", kw)), b.like(post.get("description"), String.format("%%%s%%", kw))));
        }

        String city = params.get("city");
        if (city != null && !city.isEmpty()) {
            predicates.add(b.like(locationJoin.get("city"), String.format("%%%s%%", city)));
        }

        String district = params.get("district");
        if (district != null && !district.isEmpty()) {
            predicates.add(b.like(locationJoin.get("district"), String.format("%%%s%%", district)));
        }

        String minPrice = params.get("minPrice");
        if (minPrice != null && !minPrice.isEmpty()) {
            predicates.add(b.greaterThanOrEqualTo(propJoin.get("price"), new BigDecimal(minPrice)));
        }

        String maxPrice = params.get("maxPrice");
        if (maxPrice != null && !maxPrice.isEmpty()) {
            predicates.add(b.lessThanOrEqualTo(propJoin.get("price"), new BigDecimal(maxPrice)));
        }

        String minAcreage = params.get("minAcreage");
        if (minAcreage != null && !minAcreage.isEmpty()) {
            predicates.add(b.greaterThanOrEqualTo(propJoin.get("acreage"), Integer.parseInt(minAcreage)));
        }

        String maxAcreage = params.get("maxAcreage");
        if (maxAcreage != null && !maxAcreage.isEmpty()) {
            predicates.add(b.lessThanOrEqualTo(propJoin.get("acreage"), Integer.parseInt(maxAcreage)));
        }

        String capacity = params.get("capacity");
        if (capacity != null && !capacity.isEmpty()) {
            predicates.add(b.equal(propJoin.get("capacity"), Integer.parseInt(capacity)));
        }

        q.where(predicates.toArray(new Predicate[0]));
        q.orderBy(b.desc(post.get("postId")));

        Query query = s.createQuery(q);
        String p = params.get("page");
        if (p != null && !p.isEmpty()) {
            int pageSize = Integer.parseInt(env.getProperty("posts.pageSize", "2"));
            int start = (Integer.parseInt(p) - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }

        return (List<Post>) query.getResultList();
    }

    @Override
    @Transactional
    public void addOrUpdate(Post post) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        if (post.getPostId() != null && post.getPostId() > 0) {
            s.update(post);
        } else {
            s.save(post);
        }
    }

    @Override
    public Post getPostById(int id) {
        try {
//            Session session = this.factoryBean.getObject().getCurrentSession();
//            String hql = "FROM Post p WHERE p.postId = :id";
//            Query query = session.createQuery(hql, Post.class);
//            query.setParameter("id", id);
            Session s = this.factoryBean.getObject().getCurrentSession();
            Query query = s.getNamedQuery("Post.findByPostId");
            query.setParameter("postId", id);

            return (Post) query.getSingleResult();
        } catch (NoResultException e) {
            return null;

        }
    }

    @Override
    @Transactional
    public void deletePost(int id) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Post post = this.getPostById(id);
        if (post != null) {
            s.delete(post);
        }
    }

    @Override
    public void approvePost(int postId) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Post post = this.getPostById(postId);
        if (post != null) {
            post.setStatus(true);
            post.setActived(true);
            s.update(post);
        }
    }

    @Override
    public void unActivedPost(int postId) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Post post = this.getPostById(postId);
        if (post != null) {
            post.setActived(false);
            s.update(post);
        }
    }

    @Override
    public Post getPostDetail(int postId) {
        try {
            Session s = this.factoryBean.getObject().getCurrentSession();
            String hql = "FROM Post p LEFT JOIN FETCH p.propertyDetail WHERE p.postId = :id";
            Query query = s.createQuery(hql, Post.class);
            query.setParameter("id", postId);
            return (Post) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no post found with the given postId
        }
    }

//    public double haversine(double lat1, double lon1, double lat2, double lon2) {
//        final int R = 6371; // Bán kính trái đất tính bằng km
//
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = R * c; // Tính khoảng cách
//
//        return distance;
//    }

    @Override
    public List<Post> findNearHouse(BigDecimal userLat, BigDecimal userLon, int dist) {
        Session s = this.factoryBean.getObject().getCurrentSession();

        String hql = "SELECT p FROM Post p JOIN p.location l WHERE " +
                "(6371 * acos(cos(radians(:userLat)) * cos(radians(l.latitude)) * " +
                "cos(radians(l.longitude) - radians(:userLon)) + sin(radians(:userLat)) * sin(radians(l.latitude)))) <= :distance";

        Query query = s.createQuery(hql, Post.class);
        query.setParameter("userLat", userLat);
        query.setParameter("userLon", userLon);
        query.setParameter("distance", (double) dist);

        return query.getResultList();
    }



    @Override
    public List<Post> getListreportedPosts(boolean status, boolean actived, Map<String, String> params) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);

        Join<Post, PropertyDetail> propJoin = root.join("propertyDetail", JoinType.INNER);
        Join<Post, Location> locationJoin = root.join("location", JoinType.INNER);
        Join<Post, ReportPost> reportJoin = root.join("reportPostSet", JoinType.LEFT); 

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("status"), status));
        predicates.add(builder.equal(root.get("actived"), actived));
        predicates.add(builder.greaterThan(builder.size(root.get("reportPostSet")), 0)); 
        query.groupBy(root.get("postId"));
        query.orderBy(builder.desc(builder.count(reportJoin)));
        query.where(predicates.toArray(new Predicate[0]));

        Query q = session.createQuery(query);

        String page = params.get("page");
        if (page != null && !page.isEmpty()) {
            int pageNumber = Integer.parseInt(page);
            int pageSize = Integer.parseInt(env.getProperty("posts.pageSize", "10")); 
            int start = (pageNumber - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }
        return q.getResultList();
    }


}
