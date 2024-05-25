/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Post;
import com.th.repositories.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<Post> getPosts(Map<String, String> params) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Post> q = b.createQuery(Post.class);
        Root<Post> r = q.from(Post.class);
        q.select(r);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(b.like(r.get("title"), String.format("%%%s%%", kw)));
        }

        String userId = params.get("userId");
        if (userId != null && !userId.isEmpty()) {
            predicates.add(b.equal(r.get("userId"), Integer.parseInt(userId)));
        }

        q.where(predicates.toArray(new Predicate[0]));
        q.orderBy(b.desc(r.get("postId")));

        Query query = s.createQuery(q);

        String p = params.get("page");
        if (p != null && !p.isEmpty()) {
            int pageSize = Integer.parseInt(env.getProperty("posts.pageSize"));
            int start = (Integer.parseInt(p) - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
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
        Session s = this.factoryBean.getObject().getCurrentSession();
        return s.get(Post.class, id);
    }

    @Override
    public void deletePost(int id) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Post post = this.getPostById(id);
        if (post != null) {
            s.delete(post);
        }
    }

}
