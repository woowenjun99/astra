package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.DeviceEntity;
import com.wenjun.astra_persistence.models.DeviceEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceEntityMapper {
    long countByExample(DeviceEntityExample example);

    int deleteByExample(DeviceEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceEntity row);

    int insertSelective(DeviceEntity row);

    List<DeviceEntity> selectByExample(DeviceEntityExample example);

    DeviceEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") DeviceEntity row, @Param("example") DeviceEntityExample example);

    int updateByExample(@Param("row") DeviceEntity row, @Param("example") DeviceEntityExample example);

    int updateByPrimaryKeySelective(DeviceEntity row);

    int updateByPrimaryKey(DeviceEntity row);
}
