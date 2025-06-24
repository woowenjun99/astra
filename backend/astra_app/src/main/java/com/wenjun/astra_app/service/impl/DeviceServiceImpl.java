package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.AddPushNotificationDTO;
import com.wenjun.astra_app.service.DeviceService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.DeviceEntity;
import com.wenjun.astra_persistence.repository.DeviceRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public void addPushNotificationToken(AddPushNotificationDTO request) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        DeviceEntity device = new DeviceEntity();
        device.setDeviceToken(request.getPushNotificationToken());
        device.setUid(userId);
        deviceRepository.insertSelective(device);
    }
}
