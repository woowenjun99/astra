package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateFitnessGoalDTO;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;
import com.wenjun.astra_app.model.vo.FitnessGoal;
import com.wenjun.astra_app.plugins.fitness_goals.FitnessGoalPlugin;
import com.wenjun.astra_app.plugins.fitness_goals.FitnessGoalPlugins;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.ExerciseEntity;
import com.wenjun.astra_persistence.models.FitnessGoalEntity;
import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.DailyActivity;
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
    private final FitnessGoalPlugins fitnessGoalPlugins;

    /**
     * Create a fitness goal for the user.
     *
     * @param request The payload passed from the frontend
     * @throws AstraException If the user is not logged in
     * @throws AstraException If the fitness goal category is invalid
     * @throws AstraException If the fitness goal already exist for the user
     */
    @Override
    public void createFitnessGoal(CreateFitnessGoalDTO request) throws AstraException {
        FitnessGoalCategory fitnessGoalCategory = FitnessGoalCategory.getByAlias(request.getCategory());
        AuthenticatedUser user = ThreadLocalUser.getAuthenticatedUser();
        FitnessGoalEntity existingGoal = fitnessRepository.getByPrimaryKey(fitnessGoalCategory.getAlias(), user.getUid());
        if (existingGoal != null) {
            throw new AstraException(AstraExceptionEnum.CONFLICT, "fitness goal for the user");
        }
        FitnessGoalEntity entity = new FitnessGoalEntity();
        entity.setUid(user.getUid());
        entity.setTitle(request.getTitle());
        entity.setCategory(fitnessGoalCategory.getAlias());
        entity.setTargetValue(request.getTargetValue());
        entity.setTargetDate(request.getTargetDate());
        entity.setTargetValue(request.getTargetValue());
        fitnessRepository.insertFitnessGoalSelective(entity);
    }

    private FitnessGoal convertToFitnessGoal(FitnessGoalEntity fitnessGoalEntity) throws AstraException {
        FitnessGoalCategory category = FitnessGoalCategory.getByAlias(fitnessGoalEntity.getCategory());
        FitnessGoalPlugin plugin = fitnessGoalPlugins.getPlugin(category);
        Double currentValue = plugin.getCurrentValue();
        FitnessGoal fitnessGoal = FitnessGoal
                .builder()
                .category(category.getAlias())
                .currentValue(currentValue)
                .description(fitnessGoalEntity.getDescription())
                .startingValue(plugin.getStartingValue())
                .targetDate(fitnessGoalEntity.getTargetDate())
                .targetValue(fitnessGoalEntity.getTargetValue())
                .title(fitnessGoalEntity.getTitle())
                .unit(category.getUnit())
                .build();
        return fitnessGoal;
    }

    /**
     * Get all the fitness goals for the user
     *
     * @return a list of fitness goals
     * @throws AstraException If the user is not logged in
     */
    @Override
    public List<FitnessGoal> getFitnessGoals() throws AstraException {
        AuthenticatedUser user = ThreadLocalUser.getAuthenticatedUser();
        List<FitnessGoalEntity> fitnessGoals = fitnessRepository.getFitnessGoalsByUid(user.getUid());
        List<FitnessGoal> response = new ArrayList<>();
        for (FitnessGoalEntity fitnessGoalEntity: fitnessGoals) {
            FitnessGoal fitnessGoal = convertToFitnessGoal(fitnessGoalEntity);
            response.add(fitnessGoal);
        }
        return response;
    }

    @Override
    public List<WorkoutLogEntity> getWorkouts() throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        return fitnessRepository.getWorkouts(userId);
    }

    @Override
    public List<DailyActivity> getWeeklyActivity() throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        return fitnessRepository.getWeeklyActivity(userId);
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
}
