package com.wenjun.astra_app.util;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
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

    public static AuthenticatedUser getAuthenticatedUser() throws AstraException {
        if (user.get() == null) {
            throw new AstraException(AstraExceptionEnum.UNAUTHORIZED);
        }
        return user.get();
    }
}
