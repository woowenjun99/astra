package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.vo.GetWorkoutVO;
import com.wenjun.astra_app.model.vo.GetWorkoutsVO;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.ExerciseEntity;
import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;
import com.wenjun.astra_persistence.repository.FitnessRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;

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
    private WorkoutLogEntity setWorkoutLogFields(WorkoutLogEntity workoutLog, CreateWorkoutDTO request) {
        workoutLog.setDate(request.getDate());
        workoutLog.setTitle(request.getTitle());
        workoutLog.setCaloriesBurnt(request.getCaloriesBurnt());
        workoutLog.setDuration(request.getDuration());
        workoutLog.setIntensity(request.getIntensity());
        workoutLog.setWorkoutType(request.getWorkoutType());
        return workoutLog;
    }

    /**
     * Save the runs provided by the frontend in the database
     *
     * @param runningDTOs The runs provided by the frontend
     * @param workoutLogId The workout log associated with the run
     */
    private void saveRuns(List<CreateWorkoutDTO.RunningDTO> runningDTOs, Long workoutLogId) {
        if (CollectionUtils.isEmpty(runningDTOs)) {
            return;
        }
        List<RunEntity> runs = new ArrayList<>(runningDTOs.size());
        for (int i = 0; i < runningDTOs.size(); ++i) {
            CreateWorkoutDTO.RunningDTO entity = runningDTOs.get(i);
            RunEntity run = new RunEntity();
            run.setWorkoutLogId(workoutLogId);
            run.setDistance(entity.getDistance());
            run.setIndex(i);
            run.setDuration(entity.getDuration());
            runs.add(run);
        }
        if (CollectionUtils.isNotEmpty(runs)) {
            fitnessRepository.batchInsertRuns(runs);
        }
    }

    /**
     * Save the exercises provided by the frontend in the database
     *
     * @param exerciseDTOs The exercises provided by the frontend
     * @param workoutLogId The workout log associated with the exercise
     */
    private void saveExercises(List<CreateWorkoutDTO.ExerciseDTO> exerciseDTOs, Long workoutLogId) {
        if (CollectionUtils.isEmpty(exerciseDTOs)) {
            return;
        }
        List<ExerciseEntity> exercises = new ArrayList<>(exerciseDTOs.size());
        for (int i = 0; i < exerciseDTOs.size(); ++i) {
            CreateWorkoutDTO.ExerciseDTO entity = exerciseDTOs.get(i);
            ExerciseEntity exercise = new ExerciseEntity();
            exercise.setName(entity.getName());
            exercise.setReps(entity.getReps());
            exercise.setSets(entity.getSets());
            exercise.setWorkoutLogId(workoutLogId);
            exercise.setWeight(entity.getWeight());
            exercise.setIndex(i);
            exercises.add(exercise);
        }
        if (CollectionUtils.isNotEmpty(exercises)) {
            fitnessRepository.batchInsertExercises(exercises);
        }
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
        saveRuns(request.getRuns(), workoutLogId);
        saveExercises(request.getExercises(), workoutLogId);
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
        fitnessRepository.deleteExercisesByWorkoutId(request.getId());
        saveRuns(request.getRuns(), request.getId());
        saveExercises(request.getExercises(), request.getId());
    }

    @Override
    public GetWorkoutVO getWorkout(Long id) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        WorkoutLogEntity workoutLog = getWorkoutLogByUserIdAndId(userId, id);
        List<RunEntity> runs = fitnessRepository.getRunsByWorkoutId(workoutLog.getId());
        List<ExerciseEntity> exercises = fitnessRepository.getExerciseByWorkoutId(workoutLog.getId());
        GetWorkoutVO response = GetWorkoutVO
                .builder()
                .exercises(exercises)
                .runs(runs)
                .workout(workoutLog)
                .build();
        return response;
    }
}
