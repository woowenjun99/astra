package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.AddPushNotificationDTO;
import com.wenjun.astra_app.service.DeviceService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping
    public void addPushNotificationToken(@Valid @RequestBody AddPushNotificationDTO request) throws AstraException {
        deviceService.addPushNotificationToken(request);
    }

    @PostMapping("/test")
    public void sendTestNotification() throws AstraException {
        deviceService.sendTestNotification();
    }
}
