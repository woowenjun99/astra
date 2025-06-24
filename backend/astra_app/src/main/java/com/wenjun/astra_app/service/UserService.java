package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.AddPushNotificationDTO;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_persistence.models.UserEntity;

public interface UserService {
    /**
     * Updates the user based on the fields.
     *
     * @param request The request
     * @throws AstraException If the user is not logged in or does not exist in the DB
     */
    void updateUser(UpdateUserDTO request) throws AstraException;

    void createUserWithEmailAndPassword(CreateUserDTO request) throws AstraException;

    void deleteUser() throws AstraException;

    UserEntity getUser() throws AstraException;

    void addPushNotificationToken(AddPushNotificationDTO request) throws AstraException;
}
