package com.wenjun.astra_third_party_services.firebase.service;

import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

public class FirebaseClientImpl implements FirebaseClient {
    private final FirebaseAuth firebaseAuth;

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseClientImpl(FirebaseMessaging firebaseMessaging, FirebaseAuth firebaseAuth) {
        this.firebaseMessaging = firebaseMessaging;
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

    @Override
    public void sendPushNotification(String token) {
        try {
            Message message = Message
                    .builder()
                    .setToken(token)
                    .build();
            String response = firebaseMessaging.send(message);
        } catch(FirebaseMessagingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
