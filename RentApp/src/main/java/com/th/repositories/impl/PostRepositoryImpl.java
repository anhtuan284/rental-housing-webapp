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
    public List<Post> getPosts(int typeId, boolean status, Map<String, String> params) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Post> q = b.createQuery(Post.class);
        Root<Post> post = q.from(Post.class);

        Join<Post, PropertyDetail> propJoin = post.join("propertyDetail", JoinType.INNER);
        Join<Post, Location> locationJoin = post.join("location", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(post.get("status"), status));
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
        System.out.println(postId);
        Post post = this.getPostById(postId);
        System.out.println(post.getDescription());
        if (post != null) {
            System.out.println(post.getDescription());
            post.setStatus(true);
            s.update(post);
        }
    }
}
