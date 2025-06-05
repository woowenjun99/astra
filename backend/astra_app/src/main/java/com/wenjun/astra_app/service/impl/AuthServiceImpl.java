package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.service.AuthService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private FirebaseAuth firebaseAuth;

    @Override
    public FirebaseToken validate(String idToken) throws FirebaseAuthException {
        return firebaseAuth.verifyIdToken(idToken, true);
    }

    @Override
    public void updateUser(String uid, String newEmail) throws FirebaseAuthException {
        UpdateRequest request = new UpdateRequest(uid);
        request.setEmail(newEmail);
        firebaseAuth.updateUser(request);
    }

    @Override
    public UserRecord createUser(String email, String password) throws FirebaseAuthException {
        CreateRequest request = new CreateRequest();
        request.setEmail(email);
        request.setPassword(password);
        return firebaseAuth.createUser(request);
    }

    @Override
    public void deleteUser(String uid) throws FirebaseAuthException {
        firebaseAuth.deleteUser(uid);
    }
}
