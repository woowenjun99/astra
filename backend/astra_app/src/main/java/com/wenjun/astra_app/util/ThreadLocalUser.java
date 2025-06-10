package com.wenjun.astra_app.util;

import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

public class ThreadLocalUser {
    private static ThreadLocal<AuthenticatedUser> user = new ThreadLocal<>();

    public static void set(AuthenticatedUser authenticatedUser) {
        user.set(authenticatedUser);
    }

    public static void clear() {
        user.remove();
    }

    public static AuthenticatedUser get() {
        return user.get();
    }
}
