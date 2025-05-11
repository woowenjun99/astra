package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.UserEntityMapper;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.models.UserEntityExample;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@AllArgsConstructor
@Repository
public class UserRepository {
    @Resource
    private UserEntityMapper userEntityMapper;

    public void createUser(UserEntity user) {
        userEntityMapper.insertSelective(user);
    }

    public boolean doesUserWithEmailExists(String email) {
        UserEntityExample example = new UserEntityExample();
        example.createCriteria().andEmailEqualTo(email);
        List<UserEntity> users = userEntityMapper.selectByExample(example);
        return !CollectionUtils.isEmpty(users);
    }

    public UserEntity queryByUid(String uid) {
        return userEntityMapper.selectByPrimaryKey(uid);
    }
}
