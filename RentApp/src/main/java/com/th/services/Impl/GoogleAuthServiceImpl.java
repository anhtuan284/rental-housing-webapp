package com.th.services.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.th.services.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GoogleAuthServiceImpl implements GoogleAuthService {

    @Autowired
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @Override
    public boolean verifyGoogleToken(String idTokenString) {
        try {
            GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenString);
            return idToken != null;
        } catch (GeneralSecurityException | IOException e) {
            System.out.println("Token verification failed: " + e.getMessage());
            return false;
        }
    }
}
