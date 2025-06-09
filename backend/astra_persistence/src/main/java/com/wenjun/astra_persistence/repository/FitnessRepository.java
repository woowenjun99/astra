package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.WorkoutLogEntityMapper;
import com.wenjun.astra_persistence.mappers.manual.ManualWorkoutLogEntityMapper;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntityExample;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

import jakarta.annotation.Resource;

@AllArgsConstructor
@Repository
public class FitnessRepository {
    @Resource
    private final ManualWorkoutLogEntityMapper manualWorkoutLogEntityMapper;
    @Resource
    private final WorkoutLogEntityMapper workoutLogEntityMapper;

    public List<WorkoutLogEntity> getWorkoutLogEntity(String userId, Long pageSize, Long pageNo) {
        return manualWorkoutLogEntityMapper.getWorkoutLogs(userId, pageSize, pageNo);
    }

    public Long getTotalWorkoutLogEntityCount(String userId) {
        WorkoutLogEntityExample example = new WorkoutLogEntityExample();
        example
                .createCriteria()
                .andUserIdEqualTo(userId);
        return workoutLogEntityMapper.countByExample(example);
    }
}
