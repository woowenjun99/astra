package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.ScheduledEntity;
import com.wenjun.astra_persistence.models.ScheduledEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduledEntityMapper {
    long countByExample(ScheduledEntityExample example);

    int deleteByExample(ScheduledEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ScheduledEntity row);

    int insertSelective(ScheduledEntity row);

    List<ScheduledEntity> selectByExample(ScheduledEntityExample example);

    ScheduledEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") ScheduledEntity row, @Param("example") ScheduledEntityExample example);

    int updateByExample(@Param("row") ScheduledEntity row, @Param("example") ScheduledEntityExample example);

    int updateByPrimaryKeySelective(ScheduledEntity row);

    int updateByPrimaryKey(ScheduledEntity row);
}
