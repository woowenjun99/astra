package com.wenjun.astra_app.controller;

import com.wenjun.astra_persistence.models.DeviceEntity;
import com.wenjun.astra_persistence.models.ScheduledEntity;
import com.wenjun.astra_persistence.repository.DeviceRepository;
import com.wenjun.astra_persistence.repository.ScheduleRepository;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SchedulerTest {
    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private FirebaseClient firebaseClient;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Test
    public void sendNotificationEveryThirtyMinutes_ifNothingScheduled_doNothing() {
        Mockito.when(scheduleRepository.getAllScheduledActivities()).thenReturn(Collections.emptyList());
        Scheduler scheduler = new Scheduler(deviceRepository, scheduleRepository, firebaseClient);
        Assertions.assertDoesNotThrow(() -> scheduler.sendNotificationEveryThirtyMinutes());
        Mockito.verify(deviceRepository, Mockito.never()).getDevicesByUserIds(Mockito.any());
    }

    @Test
    public void sendNotificationEveryThirtyMinutes_differentMessagesScheduled_sendToDifferentUsers() {
        ScheduledEntity scheduledOne = new ScheduledEntity();
        ScheduledEntity scheduledTwo = new ScheduledEntity();
        scheduledOne.setUid("a");
        scheduledTwo.setUid("b");
        DeviceEntity deviceOne = new DeviceEntity();
        deviceOne.setUid("a");
        DeviceEntity deviceTwo = new DeviceEntity();
        deviceTwo.setUid("b");
        List<ScheduledEntity> scheduled = Arrays.asList(scheduledOne, scheduledTwo);
        Mockito.when(scheduleRepository.getAllScheduledActivities()).thenReturn(scheduled);
        Mockito.when(deviceRepository.getDevicesByUserIds(Arrays.asList("a", "b"))).thenReturn(Arrays.asList(deviceOne, deviceTwo));
        Scheduler scheduler = new Scheduler(deviceRepository, scheduleRepository, firebaseClient);
        Assertions.assertDoesNotThrow(() -> scheduler.sendNotificationEveryThirtyMinutes());

        Mockito.verify(firebaseClient, Mockito.times(2)).sendPushNotification(Mockito.any(), Mockito.any(), Mockito.any());
    }
}
