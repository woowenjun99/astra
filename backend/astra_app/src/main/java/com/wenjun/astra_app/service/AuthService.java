package com.wenjun.astra_app.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public interface AuthService {
    FirebaseToken validate(String idToken) throws FirebaseAuthException;

    void updateUser(String uid, String newEmail) throws FirebaseAuthException;

    UserRecord createUser(String email, String password) throws FirebaseAuthException;

    void deleteUser(String uid) throws FirebaseAuthException;
}
