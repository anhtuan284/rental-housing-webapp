package com.th.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import java.security.Principal;
import java.util.Map;

import com.th.components.JwtService;
import com.th.pojo.Role;
import com.th.pojo.User;
import com.th.services.GoogleAuthService;
import com.th.services.RoleService;
import com.th.services.UserService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private BCryptPasswordEncoder passswordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RoleService roleSe;

    @Autowired
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @PostMapping(path = "/users/", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam Map<String, String> params, @RequestPart MultipartFile[] files) {
        User user = new User();
        user.setName(params.get("name"));
        user.setUsername(params.get("username"));
        user.setPassword(params.get("password"));
        user.setNumberPhone(params.get("phone"));
        user.setEmail(params.get("email"));
        user.setCccd(params.get("cccd"));
        user.setAddress(params.get("address"));
        if (params.get("role") != null && !params.get("role").equals("")) {
            user.setRoleId(roleSe.getRoleById(Integer.parseInt(params.get("role"))));
        }
        if (files.length > 0) {
            user.setFile(files[0]);
        }
        this.userService.addUser(user);
    }

    @PostMapping("/login/")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody User user) {
        if (this.userService.authUser(user.getUsername(), user.getPassword())) {
            String token = this.jwtService.generateTokenLogin(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }
    
    @CrossOrigin
    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getCurrentUser(Principal p) {
        User u = this.userService.getUserByUsername(p.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping(path = "/profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<User> getUserProfile(@PathVariable int id) {
        User u = this.userService.getUserProfile(id);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping(path = "/CheckUserByEmail/")
    public ResponseEntity<String> getUserProfile(@RequestBody Map<String, String> params) {
        try {
            User user = userService.getUserByEmail(params.get("userEmail"));
            if (user != null) {
                return ResponseEntity.ok("User found: " + user.getEmail());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    @CrossOrigin
    @PostMapping("/google-login")
    public ResponseEntity<String> googleLogin(@RequestBody Map<String, String> params) throws GeneralSecurityException, IOException {
        String idToken = params.get("idToken");

        GoogleIdToken token = googleIdTokenVerifier.verify(idToken);
        if (token != null) {

            String email = token.getPayload().getEmail();
            User user = userService.getUserByEmail(email);
            if (user == null) {
                user = new User();
                user.setFile(null);
                user.setUsername(email);
                user.setPassword(token.getPayload().getSubject());
                Role role = roleSe.getRoleById(3);
                user.setRoleId(role);
            }
            user.setEmail(email);
            user.setName(params.get("name"));
            user.setAvatar(params.get("imageUrl"));
            userService.mergeGgAcc(user);
            String JWToken = this.jwtService.generateTokenLogin(user.getUsername());
            return new ResponseEntity<>(JWToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid ID token", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/profile/{id}/update", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
    })
    @CrossOrigin
    public ResponseEntity<String> updateUserProfile(
            @PathVariable int id,
            @RequestParam Map<String, String> params,
            @RequestPart(required = false) MultipartFile[] files) {

        try {
            User u = this.userService.getUserProfile(id);
            if (u == null) {
                return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
            }
            u.setName(params.getOrDefault("name", u.getName()));
            u.setUsername(params.getOrDefault("username", u.getUsername()));
            u.setAddress(params.getOrDefault("address", u.getAddress()));
            u.setCccd(params.getOrDefault("cccd", u.getCccd()));
            u.setEmail(params.getOrDefault("email", u.getEmail()));
            u.setNumberPhone(params.getOrDefault("phone", u.getNumberPhone()));
            u.setPassword(params.getOrDefault("password", u.getPassword()));
            if (params.get("password") != null) {
                u.setPassword(passswordEncoder.encode(params.get("password")));
            }
            if (files != null && files.length > 0) {
                u.setFile(files[0]);
            }

            this.userService.addOrUpdate(u);
            return new ResponseEntity<>("User profile updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the profile.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
