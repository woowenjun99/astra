package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.UserEntityMapper;
import com.wenjun.astra_persistence.models.UserEntity;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

@Repository
public class UserRepository {
    @Resource
    private UserEntityMapper userEntityMapper;

    public UserEntity getUserByUid(String uid) {
        return userEntityMapper.selectByPrimaryKey(uid);
    }

    public void updateByPrimaryKey(UserEntity user) {
        userEntityMapper.updateByPrimaryKey(user);
    }
}
