package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.DeviceEntityMapper;
import com.wenjun.astra_persistence.models.DeviceEntity;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

@Repository
public class DeviceRepository {
    @Resource
    private DeviceEntityMapper deviceEntityMapper;

    public void insertSelective(DeviceEntity device) {
        deviceEntityMapper.insertSelective(device);
    }
}
