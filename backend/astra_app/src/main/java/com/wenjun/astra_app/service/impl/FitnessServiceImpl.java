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
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;

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

        // Create the workout log first
        WorkoutLogEntity workoutLog = new WorkoutLogEntity();
        workoutLog.setDate(request.getDate());
        workoutLog.setTitle(request.getTitle());
        workoutLog.setUid(userId);
        workoutLog.setCaloriesBurnt(request.getCaloriesBurnt());
        workoutLog.setDuration(request.getDuration());
        workoutLog.setIntensity(request.getIntensity());
        workoutLog.setWorkoutType(request.getWorkoutType());
        Long workoutLogId = fitnessRepository.createWorkout(workoutLog);
        log.info("Workout Entity created: {}", workoutLogId);

        // Create the runs
        List<RunEntity> runs = new ArrayList<>(request.getRuns().size());
        for (int i = 0; i < request.getRuns().size(); ++i) {
            CreateWorkoutDTO.RunningDTO entity = request.getRuns().get(i);
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

        // Create the normal exercise
        List<ExerciseEntity> exercises = new ArrayList<>(request.getExercises().size());
        for (int i = 0; i < request.getExercises().size(); ++i) {
            CreateWorkoutDTO.ExerciseDTO entity = request.getExercises().get(i);
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
        if (request.getId() == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "ID cannot be null");
        }
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        WorkoutLogEntity entity = fitnessRepository.getWorkoutByUidAndId(request.getId(), userId);
        if (entity == null) {
            throw new AstraException(AstraExceptionEnum.RESOURCE_NOT_FOUND_EXCEPTION, "workout with id " + request.getId());
        }
        fitnessRepository.deleteWorkout(userId, request.getId());
        createWorkout(request);
    }

    @Override
    public GetWorkoutVO getWorkout(Long id) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        WorkoutLogEntity workoutLog = fitnessRepository.getWorkoutByUidAndId(id, userId);
        if (workoutLog == null) {
            throw new AstraException(AstraExceptionEnum.RESOURCE_NOT_FOUND_EXCEPTION, "workout with id " + id);
        }
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
