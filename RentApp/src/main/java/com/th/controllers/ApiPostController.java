/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.th.controllers;

import com.th.pojo.Location;
import com.th.pojo.Post;
import com.th.pojo.PropertyDetail;
import com.th.pojo.User;
import com.th.services.ImageService;
import com.th.services.LocationService;
import com.th.services.NotificationService;
import com.th.services.PostService;
import com.th.services.PropertyDetailService;
//import com.th.services.PropertyDetailService;
import com.th.services.TypeOfPostService;
import com.th.services.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author voquochuy
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiPostController {

    @Autowired
    private PostService postSe;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeOfPostService typeService;

    @Autowired
    private ImageService imgSe;

    @Autowired
    private PropertyDetailService propSe;

    @Autowired
    private LocationService locationSe;

    @GetMapping("/PostOfRenter/")
    public ResponseEntity<List<Post>> PostOfRenter(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.postSe.getPostOfRenter(params), HttpStatus.OK);
    }
     @GetMapping("/PostOfLandlord/")
    public ResponseEntity<List<Post>> PostOfLandlord(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.postSe.getPostOfLandlord(params), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return new ResponseEntity<>("Current user: " + currentUser.getUsername(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/posts")
    @Transactional
    public ResponseEntity<String> createLeasePost(@RequestParam Map<String, String> params, @RequestPart MultipartFile[] files) {
        try {
            User user = userService.getUserByUsername("admin");
            Post post = new Post();
            PropertyDetail prop = new PropertyDetail();
            Location location = new Location();
            post.setUserId(user);
            post.setStatus(false);
            post.setTitle(params.get("title"));
            post.setDescription(params.get("description"));
            post.setTypeId(typeService.getTypeById(1));
            prop.setAcreage(params.get("acreage"));
            prop.setCapacity(Integer.valueOf(params.get("capacity")));
            prop.setPrice(params.get("price"));
            location.setAddress(params.get("address"));
            location.setCity(params.get("city"));
            location.setDistrict(params.get("district"));
            location.setLatitude(new BigDecimal(params.get("latitude")));
            location.setLongitude(new BigDecimal(params.get("longitude")));;
            postSe.addOrUpdate(post);
            propSe.savePropOfPost(post, prop);
            locationSe.saveLocationOfProp(post, location);
            imgSe.saveListImageOfPost(post, files);
//            ExecutorService executor = Executors.newFixedThreadPool(2);
//            executor.submit(() -> notiSe.addNotification(user, post));
//            executor.submit(() -> imgSe.saveListImageOfPost(post, files));
//            System.out.println("mfmmfmf");
//            executor.shutdown();

//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                System.out.println(key + ": " + value);
//            }
//            for (MultipartFile file : files) {
//                System.out.println("File name: " + file.getOriginalFilename());
//                System.out.println("Content type: " + file.getContentType());
//                System.out.println("File size: " + file.getSize());
//            }
//            System.out.println("Post userId: " + post.getUserId().getUsername());
//            System.out.println("Post status: " + post.getStatus());
//            System.out.println("Post title: " + post.getTitle());
//            System.out.println("Post description: " + post.getDescription());
//
//            System.out.println("Property acreage: " + prop.getAcreage());
//            System.out.println("Property capacity: " + prop.getCapacity());
//            System.out.println("Property price: " + prop.getPrice());
//
//            System.out.println("Location address: " + location.getAddress());
//            System.out.println("Location city: " + location.getCity());
//            System.out.println("Location district: " + location.getDistrict());
//            System.out.println("Location latitude: " + location.getLatitude());
//            System.out.println("Location longitude: " + location.getLongitude());
            return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
