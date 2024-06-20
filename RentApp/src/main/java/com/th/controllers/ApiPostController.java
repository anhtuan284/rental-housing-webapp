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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author voquochuy
 */
@RestController
@RequestMapping("/")
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

    @GetMapping("/api/PostOfRenter/")
    public ResponseEntity<List<Post>> PostOfRenter(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.postSe.getPostOfRenter(params), HttpStatus.OK);
    }

    @GetMapping("/api/PostOfLandlord/")
    public ResponseEntity<List<Post>> PostOfLandlord(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.postSe.getPostOfLandlord(params), HttpStatus.OK);
    }

    @GetMapping("/api/test")
    public ResponseEntity<String> test() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return new ResponseEntity<>("Current user: " + currentUser.getUsername(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("/api/posts")
    @Transactional
    public ResponseEntity<String> createLeasePost(@RequestParam Map<String, String> params, @RequestPart MultipartFile[] files) {
        try {
            User user = userService.getCurrentUser();
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            if (files.length < 3) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Min is 3 image");
            }
            Post post = new Post();
            PropertyDetail prop = new PropertyDetail();
            Location location = new Location();

            post.setUserId(user);
            post.setStatus(false);
            post.setTitle(params.get("title"));
            post.setDescription(params.get("description"));
            post.setTypeId(typeService.getTypeById(1));

            prop.setAcreage(Integer.parseInt(params.get("acreage")));
            prop.setCapacity(Integer.valueOf(params.get("capacity")));
            prop.setPrice(new BigDecimal(params.get("price"))); // Correctly convert String to BigDecimal

            location.setAddress(params.get("address"));
            location.setCity(params.get("city"));
            location.setDistrict(params.get("district"));
            location.setLatitude(new BigDecimal(params.get("latitude")));
            location.setLongitude(new BigDecimal(params.get("longitude")));;

            postSe.addOrUpdate(post);
            propSe.savePropOfPost(post, prop);
            locationSe.saveLocationOfProp(post, location);
            imgSe.saveListImageOfPost(post, files);

            return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable(value = "postId") Integer postId) {
        System.out.println(this.postSe.getPostById(postId));
        return new ResponseEntity<>(this.postSe.getPostById(postId), HttpStatus.OK);
    }
}
