package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.UserEntityMapper;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.models.UserEntityExample;

import org.springframework.stereotype.Repository;

import java.util.List;

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

    public boolean isEmailInUse(String email) {
        UserEntityExample example = new UserEntityExample();
        example.createCriteria().andEmailEqualTo(email);
        List<UserEntity> users = userEntityMapper.selectByExample(example);
        return !users.isEmpty();
    }

    public void insertSelective(UserEntity user) {
        userEntityMapper.insertSelective(user);
    }

    public void deleteByUid(String uid) {
        userEntityMapper.deleteByPrimaryKey(uid);
    }
}
