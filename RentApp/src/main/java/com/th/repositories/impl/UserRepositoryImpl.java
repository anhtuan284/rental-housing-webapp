/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Role;
import com.th.pojo.User;
import com.th.repositories.RoleRepository;
import com.th.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author voquochuy
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT u FROM User u WHERE u.username = :username");
        q.setParameter("username", username);
        return (User) q.getSingleResult();
    }

    @Override
    public User getUserById(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, userId);
    }

    @Override
    public void addUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        Role role = roleRepository.getRoleById(5);
        System.out.println(role);
        user.setRoleId(role);
        s.save(user);
    }

    @Override
    public List<User> getAllUser(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root<User> user = q.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(b.like(user.get("username"), String.format("%%%s%%", kw)));
        }

        q.where(predicates.toArray(new Predicate[0]));

        q.orderBy(b.desc(user.get("id")));

        Query query = s.createQuery(q);

        String p = params.get("page");
        if (p != null && !p.isEmpty()) {
            int pageSize = Integer.parseInt(env.getProperty("posts.pageSize"));
            int start = (Integer.parseInt(p) - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }

        List<User> user_list = query.getResultList();
        return user_list;
    }

    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passEncoder.matches(password, u.getPassword());
    }
    @Override
    public List<Integer> getListIdFollower(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        System.out.println(user.getId());
        String hql = "SELECT f.followerId.id FROM Follow f WHERE f.followeeId.id = :userId";
        Query query = s.createQuery(hql);
        query.setParameter("userId", user.getId());
        return (List<Integer>) query.getResultList();
    }
}
