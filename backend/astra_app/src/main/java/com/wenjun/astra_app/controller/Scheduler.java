package com.wenjun.astra_app.controller;

import com.wenjun.astra_persistence.models.DeviceEntity;
import com.wenjun.astra_persistence.models.ScheduledEntity;
import com.wenjun.astra_persistence.repository.DeviceRepository;
import com.wenjun.astra_persistence.repository.ScheduleRepository;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
@Slf4j
public class Scheduler {
    private final DeviceRepository deviceRepository;

    private final ScheduleRepository scheduleRepository;

    private final FirebaseClient firebaseClient;

    @Scheduled(cron = "0 0/30 * * * *")
    public void sendNotificationEveryThirtyMinutes() {
        List<ScheduledEntity> scheduled = scheduleRepository.getAllScheduledActivities();

        if (CollectionUtils.isEmpty(scheduled)) {
            return;
        }

        List<String> userIds = scheduled
                .stream()
                .map(ScheduledEntity::getUid)
                .distinct()
                .collect(Collectors.toList());

        List<DeviceEntity> devices = deviceRepository.getDevicesByUserIds(userIds);

        Map<String, List<String>> userIdToDevices = new HashMap<>();

        devices.forEach(device -> {
            if (!userIdToDevices.containsKey(device.getUid())) {
                userIdToDevices.put(device.getUid(), new ArrayList<>());
            }
            userIdToDevices.get(device.getUid()).add(device.getDeviceToken());
        });

        scheduled.forEach(scheduledEntity -> {
            firebaseClient.sendPushNotification(
                    userIdToDevices.get(scheduledEntity.getUid()),
                    scheduledEntity.getTitle(),
                    scheduledEntity.getBody()
            );
        });
    }
}
