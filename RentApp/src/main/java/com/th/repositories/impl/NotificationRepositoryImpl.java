/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.repositories.impl;

import com.th.pojo.Notification;
import com.th.pojo.Post;
import com.th.pojo.User;
import com.th.repositories.NotificationRepository;
import com.th.services.EmailService;
import com.th.services.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
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
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean session;

    @Autowired
    private UserService userSe;

    @Autowired
    private EmailService emailSe;

    @Override
    @Transactional
    public void addNotification(User user, Post post) {
        try {
            Session session = this.session.getObject().getCurrentSession();
            if (post.getStatus()) {
                List<User> followers = userSe.getListUserFollower(user);
                List<Notification> notifications = new ArrayList<>();

                int numThreads = Runtime.getRuntime().availableProcessors(); // Số lượng bộ xử lý có sẵn
                ExecutorService executor = Executors.newFixedThreadPool(numThreads);

                for (User follower : followers) {
                    executor.submit(() -> {
                        Notification notification = new Notification();
                        notification.setMessage(user.getName() + " đã đăng tin mới");
                        notification.setPostId(post);
                        notification.setUserId(follower.getId());
                        synchronized (notifications) {
                            notifications.add(notification);
                        }
                        String subject = "Thông báo: Bài đăng mới từ trang web hỗ trợ thuê trọ";
                        String body = "<html><body>"
                                + "<h2>Xin chào " + follower.getName() + ",</h2>"
                                + "<p>Có một bài đăng mới từ " + user.getName() + ".</p>"
                                + "<p>Bạn có thể xem chi tiết bài đăng tại đây:</p>"
                                + "<a href=\"http://localhost:8080/RentApp/\">" + notification.getMessage() + "</a>"
                                + "</body></html>";
                        emailSe.sendEmail(follower.getEmail(), subject, body);
                    });
                }
                executor.shutdown();
                boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES); // Chờ tối đa 1 phút cho tất cả các nhiệm vụ hoàn thành

                if (finished) {
                    for (Notification notification : notifications) {
                        session.save(notification);
                    }
                } else {
                    // Xử lý trường hợp các nhiệm vụ không hoàn thành trong thời gian chờ
                    System.err.println("Không thể gửi tất cả các email trong thời gian chờ.");
                }
            } else {
                Notification notification = new Notification();
                notification.setMessage("Bài viết đã bị admin từ chối");
                notification.setPostId(post);
                notification.setUserId(user.getId());

                String subject = "Thông báo: Bài đăng từ trang web hỗ trợ thuê trọ";
                String body = "<html><body>"
                        + "<h2>Xin chào " + user.getName() + ",</h2>"
                        + "<p><strong>Bài đăng cũ của bạn có vấn đề và đã bị từ chối.</strong></p>"
                        + "<p>Bạn có thể xem chi tiết bài đăng tại đây:</p>"
                        + "<a href=\"http://localhost:8080/RentApp/\">" + notification.getMessage() + "</a>"
                        + "</body></html>";
                emailSe.sendEmail(user.getEmail(), subject, body);
                session.save(notification);

            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(NotificationRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Notification> listNoti(int userId) {
        Session s = this.session.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Notification> q = b.createQuery(Notification.class);
        Root<Notification> noti = q.from(Notification.class);
        Join< Notification, Post> propJoin = noti.join("postId", JoinType.INNER);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(noti.get("userId"), userId));
        q.where(predicates.toArray(new Predicate[0]));
        q.orderBy(b.desc(noti.get("notificationId")));
        Query query = s.createQuery(q);
        return (List<Notification>) query.getResultList();
    }

}
