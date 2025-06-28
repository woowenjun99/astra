package com.wenjun.astra_third_party_services.firebase.service;

import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import java.util.List;

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
    public void sendPushNotification(List<String> tokens, String title, String body) {
        try {
            Notification notification = Notification
                    .builder()
                    .setBody(body)
                    .setTitle(title)
                    .build();

            MulticastMessage messages = MulticastMessage
                    .builder()
                    .addAllTokens(tokens)
                    .putData("key", "value")
                    .build();

            BatchResponse batchResponse = firebaseMessaging.sendEachForMulticast(messages);

            List<SendResponse> responses = batchResponse.getResponses();

            responses.forEach(response -> {
                System.out.println(response.isSuccessful());
                System.out.println(response.getMessageId());
                System.out.println(response.getException());
            });
        } catch(FirebaseMessagingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
