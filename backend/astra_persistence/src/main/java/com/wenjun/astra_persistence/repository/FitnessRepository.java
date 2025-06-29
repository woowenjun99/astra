package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.RunEntityMapper;
import com.wenjun.astra_persistence.mappers.StrengthTrainingEntityMapper;
import com.wenjun.astra_persistence.mappers.WorkoutLogEntityMapper;
import com.wenjun.astra_persistence.mappers.manual.ManualWorkoutLogEntityMapper;
import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.RunEntityExample;
import com.wenjun.astra_persistence.models.StrengthTrainingEntity;
import com.wenjun.astra_persistence.models.StrengthTrainingEntityExample;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntityExample;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import jakarta.annotation.Resource;

@Repository
public class FitnessRepository {
    @Resource
    private ManualWorkoutLogEntityMapper manualWorkoutLogEntityMapper;

    @Resource
    private WorkoutLogEntityMapper workoutLogEntityMapper;

    @Resource
    private RunEntityMapper runEntityMapper;

    @Resource
    private StrengthTrainingEntityMapper strengthTrainingEntityMapper;

    public List<WorkoutLogEntity> getWorkouts(String userId, Long pageSize, Long pageNo, String workoutType, String intensity) {
        return manualWorkoutLogEntityMapper.getWorkouts(userId, pageSize, pageSize * pageNo, workoutType, intensity);
    }

    public Long createWorkout(WorkoutLogEntity workoutLog) {
        return manualWorkoutLogEntityMapper.createWorkoutLog(workoutLog);
    }

    public void batchInsertRuns(List<RunEntity> runs) {
        manualWorkoutLogEntityMapper.batchInsertRuns(runs);
    }

    public void batchInsertStrengthTraining(List<StrengthTrainingEntity> exercises) {
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

    public Long getWorkoutCount(String userId, String workoutType, String intensity) {
        WorkoutLogEntityExample example = new WorkoutLogEntityExample();
        WorkoutLogEntityExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(userId);
        if (workoutType != null) {
            criteria.andWorkoutTypeEqualTo(workoutType);
        }
        if (intensity != null) {
            criteria.andIntensityEqualTo(intensity);
        }
        return workoutLogEntityMapper.countByExample(example);
    }

    public WorkoutLogEntity getWorkoutByUidAndId(Long id, String userId) {
        WorkoutLogEntityExample example = new WorkoutLogEntityExample();
        example.createCriteria().andUidEqualTo(userId).andIdEqualTo(id);
        List<WorkoutLogEntity> results = workoutLogEntityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        return results.get(0);
    }

    public List<RunEntity> getRunsByWorkoutId(Long id) {
        RunEntityExample example = new RunEntityExample();
        example
                .createCriteria()
                .andWorkoutLogIdEqualTo(id);
        example.setOrderByClause("index desc");
        return runEntityMapper.selectByExample(example);
    }

    public List<StrengthTrainingEntity> getStrengthTrainingByWorkoutId(Long id) {
        StrengthTrainingEntityExample example = new StrengthTrainingEntityExample();
        example
                .createCriteria()
                .andWorkoutLogIdEqualTo(id);
        example.setOrderByClause("index desc");
        return strengthTrainingEntityMapper.selectByExample(example);
    }

    public void updateWorkoutByPrimaryKey(WorkoutLogEntity workout) {
        workoutLogEntityMapper.updateByPrimaryKey(workout);
    }

    public void deleteRunsByWorkoutId(Long workoutId) {
        RunEntityExample example = new RunEntityExample();
        example.createCriteria().andWorkoutLogIdEqualTo(workoutId);
        runEntityMapper.deleteByExample(example);
    }

    public void deleteStrengthTrainingByWorkoutId(Long workoutId) {
        StrengthTrainingEntityExample example = new StrengthTrainingEntityExample();
        example.createCriteria().andWorkoutLogIdEqualTo(workoutId);
        strengthTrainingEntityMapper.deleteByExample(example);
    }
}
