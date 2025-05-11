package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.models.UserEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserEntityMapper {
    long countByExample(UserEntityExample example);

    int deleteByExample(UserEntityExample example);

    int deleteByPrimaryKey(String uid);

    int insert(UserEntity row);

    int insertSelective(UserEntity row);

    List<UserEntity> selectByExample(UserEntityExample example);

    UserEntity selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("row") UserEntity row, @Param("example") UserEntityExample example);

    int updateByExample(@Param("row") UserEntity row, @Param("example") UserEntityExample example);

    int updateByPrimaryKeySelective(UserEntity row);

    int updateByPrimaryKey(UserEntity row);
}
