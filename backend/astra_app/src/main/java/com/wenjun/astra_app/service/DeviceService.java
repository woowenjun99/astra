package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.AddPushNotificationDTO;

public interface DeviceService {
    /**
     * Adds the push notification token to the Database
     *
     * @param request Contains the push notification token
     */
    void addPushNotificationToken(AddPushNotificationDTO request) throws AstraException;
}
