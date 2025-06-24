package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.DeviceEntityMapper;
import com.wenjun.astra_persistence.models.DeviceEntity;
import com.wenjun.astra_persistence.models.DeviceEntityExample;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

@Repository
public class DeviceRepository {
    @Resource
    private DeviceEntityMapper deviceEntityMapper;

    public void insertSelective(DeviceEntity device) {
        deviceEntityMapper.insertSelective(device);
    }

    public void deleteByUserIdAndPushNotificationToken(String userId, String notificationToken) {
        DeviceEntityExample example = new DeviceEntityExample();
        example
                .createCriteria()
                .andUidEqualTo(userId)
                .andDeviceTokenEqualTo(notificationToken);
        deviceEntityMapper.deleteByExample(example);
    }
}
