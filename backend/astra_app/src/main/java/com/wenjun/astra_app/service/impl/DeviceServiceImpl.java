package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.AddPushNotificationDTO;
import com.wenjun.astra_app.service.DeviceService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.DeviceEntity;
import com.wenjun.astra_persistence.repository.DeviceRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final FirebaseClient firebaseClient;

    @Override
    public void addPushNotificationToken(AddPushNotificationDTO request) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        DeviceEntity existingDevice = deviceRepository.getDeviceByToken(userId, request.getPushNotificationToken());
        if (existingDevice != null) {
            return;
        }
        DeviceEntity device = new DeviceEntity();
        device.setDeviceToken(request.getPushNotificationToken());
        device.setUid(userId);
        deviceRepository.insertSelective(device);
    }

    @Override
    public void deletePushNotificationToken(String notificationToken) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        deviceRepository.deleteByUserIdAndPushNotificationToken(userId, notificationToken);
    }

    @Override
    public void sendTestNotification() throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        List<DeviceEntity> devices = deviceRepository.getDevicesByUserId(userId);
        if (CollectionUtils.isEmpty(devices) || devices.size() > 500) {
            log.error("Unable to send notifications");
            return;
        }
        List<String> tokens = devices.stream().map(DeviceEntity::getDeviceToken).collect(Collectors.toList());
        firebaseClient.sendPushNotification(tokens, "Test", "This is a test notification");
    }
}
