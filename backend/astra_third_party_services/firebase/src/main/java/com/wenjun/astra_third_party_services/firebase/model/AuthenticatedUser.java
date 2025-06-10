package com.wenjun.astra_third_party_services.firebase.model;

public class AuthenticatedUser {
    private final String uid;

    public AuthenticatedUser(String userId) {
        this.uid = userId;
    }

    public String getUid() {
        return this.uid;
    }
}
