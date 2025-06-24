package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.FitnessGoalEntityMapper;
import com.wenjun.astra_persistence.mappers.RunEntityMapper;
import com.wenjun.astra_persistence.mappers.WorkoutLogEntityMapper;
import com.wenjun.astra_persistence.mappers.manual.ManualDailyLogEntityMapper;
import com.wenjun.astra_persistence.mappers.manual.ManualWorkoutLogEntityMapper;
import com.wenjun.astra_persistence.models.*;
import com.wenjun.astra_persistence.models.manual.DailyActivity;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;

import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import jakarta.annotation.Resource;

@Repository
public class FitnessRepository {
    @Resource
    private FitnessGoalEntityMapper fitnessGoalEntityMapper;

    @Resource
    private ManualDailyLogEntityMapper manualDailyLogEntityMapper;

    @Resource
    private ManualWorkoutLogEntityMapper manualWorkoutLogEntityMapper;

    @Resource
    private RunEntityMapper runEntityMapper;

    @Resource
    private WorkoutLogEntityMapper workoutLogEntityMapper;

    public void insertFitnessGoalSelective(FitnessGoalEntity fitnessGoalEntity) {
        fitnessGoalEntityMapper.insertSelective(fitnessGoalEntity);
    }

    public FitnessGoalEntity getByPrimaryKey(String category, String uid) {
        FitnessGoalEntityKey primaryKey = new FitnessGoalEntityKey();
        primaryKey.setUid(uid);
        primaryKey.setCategory(category);
        return fitnessGoalEntityMapper.selectByPrimaryKey(primaryKey);
    }

    public List<FitnessGoalEntity> getFitnessGoalsByUid(String uid) {
        FitnessGoalEntityExample example = new FitnessGoalEntityExample();
        example.createCriteria().andUidEqualTo(uid);
        return fitnessGoalEntityMapper.selectByExample(example);
    }

    public DailyLogEntity getRecentDailyLogsByUserId(String uid, Boolean isDescending) {
        return manualDailyLogEntityMapper.getMostRecentDailyLog(uid, isDescending);
    }

    public List<WorkoutLogEntity> getWorkouts(String userId) {
        return manualWorkoutLogEntityMapper.getWorkouts(userId, 3L, 0L);
    }

    public List<DailyActivity> getWeeklyActivity(String userId) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return manualWorkoutLogEntityMapper.getWeeklyActivity(userId, monday, sunday);
    }

    public Long createWorkout(WorkoutLogEntity workoutLog) {
        return manualWorkoutLogEntityMapper.createWorkoutLog(workoutLog);
    }

    public void batchInsertRuns(List<RunEntity> runs) {
        manualWorkoutLogEntityMapper.batchInsertRuns(runs);
    }

    public void batchInsertExercises(List<ExerciseEntity> exercises) {
        manualWorkoutLogEntityMapper.batchInsertExercises(exercises);
    }

    public WorkoutMetadata getWorkoutMetadata(String userId) {
        return manualWorkoutLogEntityMapper.getWorkoutMetadata(userId);
    }

    public void deleteWorkout(String userId, Long workoutId) {
        WorkoutLogEntityExample example = new WorkoutLogEntityExample();
        example.createCriteria().andUidEqualTo(userId).andIdEqualTo(workoutId);
        workoutLogEntityMapper.deleteByExample(example);
    }
}
