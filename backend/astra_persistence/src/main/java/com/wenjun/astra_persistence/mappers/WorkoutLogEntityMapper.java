package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkoutLogEntityMapper {
    long countByExample(WorkoutLogEntityExample example);

    int deleteByExample(WorkoutLogEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkoutLogEntity row);

    int insertSelective(WorkoutLogEntity row);

    List<WorkoutLogEntity> selectByExample(WorkoutLogEntityExample example);

    WorkoutLogEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WorkoutLogEntity row, @Param("example") WorkoutLogEntityExample example);

    int updateByExample(@Param("row") WorkoutLogEntity row, @Param("example") WorkoutLogEntityExample example);

    int updateByPrimaryKeySelective(WorkoutLogEntity row);

    int updateByPrimaryKey(WorkoutLogEntity row);
}
