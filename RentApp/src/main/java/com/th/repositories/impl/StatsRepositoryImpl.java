package com.th.repositories.impl;

import com.th.pojo.Post;
import com.th.pojo.Role;
import com.th.pojo.User;
import com.th.repositories.StatsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsUsersByRole() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<User> root = query.from(User.class);
        Root<Role> roleRoot = query.from(Role.class);

        query.multiselect(roleRoot.get("name"), builder.count(root.get("id")));
        query.where(builder.equal(root.get("roleId"), roleRoot.get("id")));
        query.groupBy(roleRoot.get("name"));

        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> statsUsersByPeriod(int year, String period) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<User> root = query.from(User.class);

        Predicate yearPredicate = builder.equal(builder.function("YEAR", Integer.class, root.get("createdDate")), year);

        query.multiselect(
                builder.function(period, String.class, root.get("createdDate")),
                builder.count(root.get("id"))
        );
        query.groupBy(builder.function(period, String.class, root.get("createdDate")));
        query.orderBy(builder.asc(builder.function(period, String.class, root.get("createdDate"))));

        query.where(yearPredicate);

        Query q = session.createQuery(query);
        return q.getResultList();
    }


    @Override
    public List<Object[]> countUsersCreated(int year, String period) {
        Session session = factory.getObject().getCurrentSession();

        // First query to get the count of users grouped by period
        String countQuery = String.format(
                "SELECT %s(u.createdDate) AS period, COUNT(u.id) AS userCount " +
                        "FROM User u " +
                        "WHERE YEAR(u.createdDate) = :year " +
                        "GROUP BY %s(u.createdDate) " +
                        "ORDER BY %s(u.createdDate)", period, period, period
        );
        Query query = session.createQuery(countQuery);
        query.setParameter("year", year);
        List<Object[]> results = query.getResultList();

        // Prepare the cumulative sum results
        List<Object[]> cumulativeResults = new ArrayList<>();
        long cumulativeSum = 0;
        for (Object[] result : results) {
            cumulativeSum += (Long) result[1]; // Assuming the count is at index 1
            cumulativeResults.add(new Object[]{result[0], cumulativeSum});
        }

        return cumulativeResults;
    }

    @Override
    public List<Object[]> countPostsByStatus() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<Post> root = query.from(Post.class);

        query.multiselect(root.get("status"), builder.count(root.get("postId")));
        query.groupBy(root.get("status"));

        Query q = session.createQuery(query);
        return q.getResultList();
    }

}
