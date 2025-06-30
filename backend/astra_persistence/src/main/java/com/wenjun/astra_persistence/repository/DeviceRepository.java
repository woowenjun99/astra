package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.DeviceEntityMapper;
import com.wenjun.astra_persistence.models.DeviceEntity;
import com.wenjun.astra_persistence.models.DeviceEntityExample;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public DeviceEntity getDeviceByToken(String userId, String pushNotificationToken) {
        DeviceEntityExample example = new DeviceEntityExample();
        example
                .createCriteria()
                .andUidEqualTo(userId)
                .andDeviceTokenEqualTo(pushNotificationToken);
        List<DeviceEntity> devices = deviceEntityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(devices)) {
            return null;
        }
        return devices.get(0);
    }

    public List<DeviceEntity> getDevicesByUserId(String userId) {
        DeviceEntityExample example = new DeviceEntityExample();
        example.createCriteria().andUidEqualTo(userId);
        return deviceEntityMapper.selectByExample(example);
    }

    public List<DeviceEntity> getDevicesByUserIds(List<String> userIds) {
        DeviceEntityExample example = new DeviceEntityExample();
        example.createCriteria().andUidIn(userIds);
        return deviceEntityMapper.selectByExample(example);
    }
}
