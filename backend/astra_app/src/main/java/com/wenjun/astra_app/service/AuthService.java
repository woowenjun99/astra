package com.wenjun.astra_app.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public interface AuthService {
    FirebaseToken validate(String idToken) throws FirebaseAuthException;

    void updateUser(String uid, String newEmail, String newName) throws FirebaseAuthException;
}
