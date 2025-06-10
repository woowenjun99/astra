package com.wenjun.astra_third_party_services.firebase.service;

import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

public interface FirebaseClient {
    /**
     * Delete the user from Firebase Auth based on the userId
     *
     * @param userId The user id to delete from firebase auth
     * @throws RuntimeException If there is an issue with deleting the user
     */
    void deleteUser(String userId);

    /**
     * Create a user with credentials using email and password
     *
     * @param email The user's email
     * @param password The user's password
     * @return An instance of the authenticated user
     * @throws RuntimeException If there is an issue with creating a new user
     */
    AuthenticatedUser createUser(String email, String password);

    /**
     * Updates the email of the user for the userId
     *
     * @param userId The user to be updated
     * @param email The email to be updated with
     * @throws RuntimeException If there is an issue with updating the user
     */
    void updateUser(String userId, String email);

    /**
     * Get the authenticated user based on the JWT token provided
     *
     * @return The authenticated user
     * @throws RuntimeException If the JWT is invalid
     */
    AuthenticatedUser getUser(String jwt);
}
