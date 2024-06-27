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
import java.util.concurrent.CompletableFuture;
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
import org.hibernate.Transaction;
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
        Session session = this.session.getObject().getCurrentSession();
        if (post.getStatus()) {
            List<User> followers = userSe.getListUserFollower(user);
            int batchSize = 20; // Kích thước batch
            for (int i = 0; i < followers.size(); i++) {
                User follower = followers.get(i);

                Notification notification = new Notification();
                notification.setMessage(user.getName() + " đã đăng tin mới");
                notification.setPostId(post);
                notification.setUserId(follower.getId());
                session.save(notification);

                CompletableFuture.runAsync(() -> {
                    String subject = "Thông báo: Bài đăng từ trang web hỗ trợ thuê trọ";
                    String body = "<html>"
                            + "<head>"
                            + "<style>"
                            + "body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; background-color: #f8f9fa; padding: 20px; }"
                            + ".container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }"
                            + ".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #dee2e6; }"
                            + ".header img { max-width: 100px; margin-bottom: 10px; }"
                            + "h2 { color: #333; }"
                            + "p { font-size: 14px; line-height: 1.5; color: #555; }"
                            + ".btn { display: inline-block; padding: 10px 20px; font-size: 14px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; }"
                            + ".btn:hover { background-color: #0056b3; }"
                            + "</style>"
                            + "</head>"
                            + "<body>"
                            + "<div class='container'>"
                            + "<div class='header'>"
                            + "<img src='https://i.etsystatic.com/19318192/r/il/31c855/4789888185/il_fullxfull.4789888185_6h9z.jpg' alt='Logo'>"
                            + "<h2>Xin chào " + follower.getName() + ",</h2>"
                            + "</div>"
                            + "<p><strong>" + user.getName() + " vừa đăng bài cho thuê mới trên trang hỗ trợ thuê phòng trọ" + "</strong></p>"
                            + "<p>Bạn có thể xem chi tiết bài đăng tại đây:</p>"
                    + "<p><a class='btn' href=\"http://localhost:5173/posts/" + post.getPostId() + "\">" + notification.getMessage() + "</a></p>"
                            + "</div>"
                            + "</body>"
                            + "</html>";
                    emailSe.sendEmail(follower.getEmail(), subject, body);
                });
                if (i % batchSize == 0 && i > 0) {
                    session.flush();
                    session.clear();
                }
            }

        } else {
            Notification notification = new Notification();
            notification.setMessage("Bài viết đã bị admin từ chối");
            notification.setPostId(post);
            notification.setUserId(user.getId());

            String subject = "Thông báo: Bài đăng từ trang web hỗ trợ thuê trọ";
            String body = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; background-color: #f8f9fa; padding: 20px; }"
                    + ".container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }"
                    + ".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #dee2e6; }"
                    + ".header img { max-width: 100px; margin-bottom: 10px; }"
                    + "h2 { color: #333; }"
                    + "p { font-size: 14px; line-height: 1.5; color: #555; }"
                    + ".btn { display: inline-block; padding: 10px 20px; font-size: 14px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; }"
                    + ".btn:hover { background-color: #0056b3; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<div class='header'>"
                    + "<img src='https://i.etsystatic.com/19318192/r/il/31c855/4789888185/il_fullxfull.4789888185_6h9z.jpg' alt='Logo'>"
                    + "<h2>Xin chào " + user.getName() + ",</h2>"
                    + "</div>"
                    + "<p><strong>Bài đăng của bạn có vấn đề và đã bị từ chối.</strong></p>"
                    + "<p>Bạn có thể xem chi tiết bài đăng tại đây:</p>"
                    + "<p><a class='btn' href=\"http://localhost:5173/posts/" + post.getPostId() + "\">" + notification.getMessage() + "</a></p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
            emailSe.sendEmail(user.getEmail(), subject, body);
            session.save(notification);

        }

    }

    @Override
    public List<Notification> listNoti(int userId) {
        Session s = this.session.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Notification> q = b.createQuery(Notification.class
        );
        Root<Notification> noti = q.from(Notification.class
        );
        Join< Notification, Post> propJoin = noti.join("postId", JoinType.INNER);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(noti.get("userId"), userId));
        q.where(predicates.toArray(new Predicate[0]));
        q.orderBy(b.desc(noti.get("notificationId")));
        Query query = s.createQuery(q);
        return (List<Notification>) query.getResultList();
    }

    @Override
    public void addNotificationDelele(User user) {
        Session session = this.session.getObject().getCurrentSession();
        Notification notification = new Notification();
        notification.setMessage("Bài viết đã bị admin xoá");
        notification.setUserId(user.getId());
        String subject = "Thông báo: Bài đăng từ trang web hỗ trợ thuê trọ";
        String body = "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; background-color: #f8f9fa; padding: 20px; }"
                + ".container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }"
                + ".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #dee2e6; }"
                + ".header img { max-width: 100px; margin-bottom: 10px; }"
                + "h2 { color: #333; }"
                + "p { font-size: 14px; line-height: 1.5; color: #555; }"
                + ".btn { display: inline-block; padding: 10px 20px; font-size: 14px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; }"
                + ".btn:hover { background-color: #0056b3; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<img src='https://i.etsystatic.com/19318192/r/il/31c855/4789888185/il_fullxfull.4789888185_6h9z.jpg' alt='Logo'>"
                + "<h2>Xin chào " + user.getName() + ",</h2>"
                + "</div>"
                + "<p><strong>Bài đăng của bạn có vấn đề và đã bị xoá bởi Admin.</strong></p>"
                + "</div>"
                + "</body>"
                + "</html>";
        emailSe.sendEmail(user.getEmail(), subject, body);
        session.save(notification);
    }

}
