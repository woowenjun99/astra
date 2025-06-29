package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;
import com.wenjun.astra_app.model.vo.GetWorkoutVO;
import com.wenjun.astra_app.model.vo.GetWorkoutsVO;
import com.wenjun.astra_app.plugins.fitness.WorkoutTypePlugin;
import com.wenjun.astra_app.plugins.fitness.WorkoutTypePlugins;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.StrengthTrainingEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;
import com.wenjun.astra_persistence.repository.FitnessRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;
    private final WorkoutTypePlugins workoutTypePlugins;

    /**
     * Get the workout log based on the user id and the workout log id.
     *
     * @param userId The user id
     * @param workoutLogId The workout log id
     * @return The workout log if found
     * @throws AstraException If the workout id is invalid or user has no permission to view it
     * @throws AstraException If the workoutLogId is invalid
     */
    private WorkoutLogEntity getWorkoutLogByUserIdAndId(String userId, Long workoutLogId) throws AstraException {
        if (workoutLogId == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "ID cannot be null");
        }
        WorkoutLogEntity entity = fitnessRepository.getWorkoutByUidAndId(workoutLogId, userId);
        if (entity == null) {
            throw new AstraException(AstraExceptionEnum.RESOURCE_NOT_FOUND_EXCEPTION, "workout with id " + workoutLogId);
        }
        return entity;
    }

    /**
     * Modifies the workout log by setting the common fields based on the request
     *
     * @param workoutLog The workout log object to modify
     * @param request The payload from the frontend
     * @return The modified workout log
     */
    private WorkoutLogEntity setWorkoutLogFields(WorkoutLogEntity workoutLog, CreateWorkoutDTO request) throws AstraException {
        workoutLog.setDate(request.getDate());
        workoutLog.setTitle(request.getTitle());
        workoutLog.setCaloriesBurnt(request.getCaloriesBurnt());
        workoutLog.setDuration(request.getDurationToSave());
        workoutLog.setIntensity(request.getIntensity());
        workoutLog.setWorkoutType(request.getWorkoutType());
        return workoutLog;
    }

    @Override
    public GetWorkoutsVO getWorkouts(Long pageSize, Long pageNo, String workoutType, String intensity) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        List<WorkoutLogEntity> workouts = fitnessRepository.getWorkouts(userId, pageSize, pageNo, workoutType, intensity);
        Long total = fitnessRepository.getWorkoutCount(userId, workoutType, intensity);
        GetWorkoutsVO response = new GetWorkoutsVO();
        response.setWorkouts(workouts);
        response.setTotal(total);
        return response;
    }

    @Override
    public void createWorkout(CreateWorkoutDTO request) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        WorkoutLogEntity workoutLog = setWorkoutLogFields(new WorkoutLogEntity(), request);
        workoutLog.setUid(userId);
        Long workoutLogId = fitnessRepository.createWorkout(workoutLog);
        WorkoutType workoutType = WorkoutType.getByAlias(request.getWorkoutType());
        WorkoutTypePlugin plugin = workoutTypePlugins.getPlugin(workoutType);
        plugin.handleCreateWorkout(request, workoutLogId);
    }

    @Override
    public WorkoutMetadata getWorkoutMetadata() throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        return fitnessRepository.getWorkoutMetadata(userId);
    }

    @Override
    public void deleteWorkout(Long workoutId) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        fitnessRepository.deleteWorkout(userId, workoutId);
    }

    @Override
    public void editWorkout(CreateWorkoutDTO request) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        WorkoutLogEntity original = getWorkoutLogByUserIdAndId(userId, request.getId());
        WorkoutLogEntity workoutLog = setWorkoutLogFields(original, request);
        workoutLog.setDateUpdated(new Date());
        fitnessRepository.updateWorkoutByPrimaryKey(workoutLog);
        fitnessRepository.deleteRunsByWorkoutId(request.getId());
        fitnessRepository.deleteStrengthTrainingByWorkoutId(request.getId());
        WorkoutType workoutType = WorkoutType.getByAlias(request.getWorkoutType());
        WorkoutTypePlugin plugin = workoutTypePlugins.getPlugin(workoutType);
        plugin.handleCreateWorkout(request, request.getId());
    }

    @Override
    public GetWorkoutVO getWorkout(Long id) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        WorkoutLogEntity workoutLog = getWorkoutLogByUserIdAndId(userId, id);
        List<RunEntity> runs = fitnessRepository.getRunsByWorkoutId(workoutLog.getId());
        List<StrengthTrainingEntity> exercises = fitnessRepository.getStrengthTrainingByWorkoutId(workoutLog.getId());
        GetWorkoutVO response = GetWorkoutVO
                .builder()
                .exercises(exercises)
                .runs(runs)
                .workout(workoutLog)
                .build();
        return response;
    }
}
