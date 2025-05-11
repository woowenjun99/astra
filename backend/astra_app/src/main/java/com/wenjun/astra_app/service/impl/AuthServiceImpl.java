package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.service.AuthService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private FirebaseAuth firebaseAuth;

    @Override
    public UserRecord createUser(String email, String password) throws FirebaseAuthException {
        CreateRequest request = new CreateRequest();
        request.setEmail(email);
        request.setPassword(password);
        return firebaseAuth.createUser(request);
    }

    @Override
    public FirebaseToken validate(String idToken) throws FirebaseAuthException {
        return firebaseAuth.verifyIdToken(idToken, true);
    }
}
