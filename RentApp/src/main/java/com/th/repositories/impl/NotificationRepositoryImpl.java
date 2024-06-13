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
import org.hibernate.HibernateException;
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
            Session s = this.session.getObject().getCurrentSession();
            List<Integer> listId = userSe.getListIdFollower(user);
            List<Notification> notifications = new ArrayList<>();
//            int numThreads = Runtime.getRuntime().availableProcessors(); // Số lượng bộ xử lý có sẵn trên máy tính
//            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            for (Integer id : listId) {
//                executor.submit(() -> {
                    Notification notification = new Notification();
                    notification.setMessage(user.getName() + " đăng tin mới");
                    notification.setPostId(post);
                    notification.setUserId(id);
                    notifications.add(notification);
                    s.save(notification);
                    String subject = "Thông báo: Bài đăng mới từ  trang web hỗ trợ thuê trọ";
                    String body = "<html><body>"
                            + "<h2>Xin chào " + ",</h2>"
                            + "<p>Có một bài đăng mới từ " + user.getName() + ".</p>"
                            + "<p>Bạn có thể xem chi tiết bài đăng tại đây:</p>"
                            + "<a href=\"" + "http://localhost:8080/RentApp/" + "\">" + notification.getMessage() + "</a>"
                            + "</body></html>";
                    emailSe.sendEmail("2151013029huy@ou.edu.vn", subject, body);

//                });
            }
//            executor.shutdown();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

}
