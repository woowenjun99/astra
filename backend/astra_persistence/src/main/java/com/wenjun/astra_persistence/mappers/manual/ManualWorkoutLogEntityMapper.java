package com.wenjun.astra_persistence.mappers.manual;

import com.wenjun.astra_persistence.models.WorkoutLogEntity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManualWorkoutLogEntityMapper {
    List<WorkoutLogEntity> getWorkoutLogs(
            @Param("userId") String userId,
            @Param("pageSize") Long pageSize,
            @Param("pageNo") Long pageNo
    );
}
