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

        if (files.length > 0) {
            user.setFile(files[0]);
        }

        this.userService.addUser(user);
    }

    @PostMapping("/login/")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println("Login");
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
}
