package com.wenjun.astra_persistence.mappers.manual;

import com.wenjun.astra_persistence.models.ExerciseEntity;
import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.DailyActivity;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface ManualWorkoutLogEntityMapper {
    List<WorkoutLogEntity> getRecentWorkouts(@Param("userId") String userId);

    List<DailyActivity> getWeeklyActivity(
            @Param("userId") String userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Long createWorkoutLog(@Param("workoutLog") WorkoutLogEntity workoutLog);

    void batchInsertRuns(@Param("runs") List<RunEntity> runs);

    void batchInsertExercises(@Param("exercises")List<ExerciseEntity> exercises);
}
