package com.wenjun.astra_third_party_services.firebase.service;

import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public class FirebaseClientImpl implements FirebaseClient {
    private final FirebaseAuth firebaseAuth;

    public FirebaseClientImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void deleteUser(String userId) {
        try {
            firebaseAuth.deleteUser(userId);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AuthenticatedUser createUser(String email, String password) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest();
            request.setEmail(email);
            request.setPassword(password);
            UserRecord user = firebaseAuth.createUser(request);
            return new AuthenticatedUser(user.getUid());
        } catch(FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateUser(String userId, String email) {
        try {
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userId);
            request.setEmail(email);
            firebaseAuth.updateUser(request);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AuthenticatedUser getUser(String jwt) {
        try {
            FirebaseToken token = firebaseAuth.verifyIdToken(jwt, true);
            return new AuthenticatedUser(token.getUid());
        } catch(FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
