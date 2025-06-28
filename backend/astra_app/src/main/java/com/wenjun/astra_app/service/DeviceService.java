package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.AddPushNotificationDTO;

public interface DeviceService {
    /**
     * Adds the push notification token to the Database
     *
     * @param request Contains the push notification token
     * @throws AstraException If the user is not logged in
     */
    void addPushNotificationToken(AddPushNotificationDTO request) throws AstraException;

    /**
     * Deletes the push notification token from the database.
     * 2 generic uses cases are when user chooses to unsubscribe
     * and when the user logs out.
     *
     * @param notificationToken The push notification token
     * @throws AstraException If the user is not logged in
     */
    void deletePushNotificationToken(String notificationToken) throws AstraException;

    void sendTestNotification() throws AstraException;
}
