package com.wenjun.astra_app.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public interface AuthService {
    UserRecord createUser(String email, String password) throws FirebaseAuthException;

    FirebaseToken validate(String idToken) throws FirebaseAuthException;
}
