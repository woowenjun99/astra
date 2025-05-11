package com.wenjun.astra_app.service.impl;

import com.google.firebase.auth.FirebaseToken;

public class ThreadLocalUser {
    private static ThreadLocal<FirebaseToken> user = new ThreadLocal<>();

    public static void set(FirebaseToken token) {
        user.set(token);
    }

    public static void clear() {
        user.remove();
    }

    public static FirebaseToken get() {
        return user.get();
    }
}
