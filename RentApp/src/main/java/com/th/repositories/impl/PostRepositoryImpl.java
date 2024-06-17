/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Image;
import com.th.pojo.Post;
import com.th.pojo.PropertyDetail;
import com.th.pojo.User;
import com.th.repositories.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.core.env.Environment;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(post.get("status"), status));
        predicates.add(b.equal(post.get("typeId").get("typeId"), typeId));

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(b.like(post.get("title"), String.format("%%%s%%", kw)));
        }

        q.where(predicates.toArray(new Predicate[0]));
        q.orderBy(b.desc(post.get("postId")));

        Query query = s.createQuery(q);
        String p = params.get("page");
        if (p != null && !p.isEmpty()) {
            int pageSize = Integer.parseInt(env.getProperty("posts.pageSize", "5"));
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
            Session session = this.factoryBean.getObject().getCurrentSession();
            String hql = "FROM Post p WHERE p.postId = :id";
            Query query = session.createQuery(hql, Post.class);
            query.setParameter("id", id);
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
