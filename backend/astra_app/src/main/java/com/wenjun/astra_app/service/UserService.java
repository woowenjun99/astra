package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateFitnessGoalsDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_persistence.models.UserEntity;

import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {
    /**
     * Updates the user based on the fields.
     *
     * @param request The request
     * @throws AstraException If the user is not logged in or does not exist in the DB
     * @throws FirebaseAuthException If there is an issue with updating the user
     */
    void updateUser(UpdateUserDTO request) throws AstraException, FirebaseAuthException;

    void createUser(CreateUserDTO request) throws AstraException, FirebaseAuthException;

    void deleteUser() throws AstraException, FirebaseAuthException;

    UserEntity getUser() throws AstraException;

    void updateFitnessGoals(UpdateFitnessGoalsDTO request) throws AstraException;
}
