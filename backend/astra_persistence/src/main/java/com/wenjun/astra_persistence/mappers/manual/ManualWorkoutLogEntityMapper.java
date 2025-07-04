package com.wenjun.astra_persistence.mappers.manual;

import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.StrengthTrainingEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManualWorkoutLogEntityMapper {
    List<WorkoutLogEntity> getWorkouts(
            @Param("userId") String userId,
            @Param("pageSize") Long pageSize,
            @Param("offset") Long offset,
            @Param("workoutType") String workoutType,
            @Param("intensity") String intensity
    );

    Long createWorkoutLog(@Param("workoutLog") WorkoutLogEntity workoutLog);

    void batchInsertRuns(@Param("runs") List<RunEntity> runs);

    void batchInsertExercises(@Param("exercises")List<StrengthTrainingEntity> exercises);

    WorkoutMetadata getWorkoutMetadata(
            @Param("userId") String userId,
            @Param("intensity") String intensity,
            @Param("workoutType") String workoutType);
}
