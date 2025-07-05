package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateScheduledWorkoutDTO;
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
import com.wenjun.astra_persistence.models.ScheduledEntity;
import com.wenjun.astra_persistence.models.StrengthTrainingEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;
import com.wenjun.astra_persistence.repository.FitnessRepository;
import com.wenjun.astra_persistence.repository.ScheduleRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;
    private final ScheduleRepository scheduleRepository;
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
     * @param duration The duration to be saved in the workout log entity
     * @return The modified workout log
     */
    private WorkoutLogEntity setWorkoutLogFields(WorkoutLogEntity workoutLog, CreateWorkoutDTO request, Integer duration) throws AstraException {
        workoutLog.setDate(request.getDate());
        workoutLog.setTitle(request.getTitle());
        workoutLog.setCaloriesBurnt(request.getCaloriesBurnt());
        workoutLog.setDuration(duration);
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
        WorkoutType workoutType = WorkoutType.getByAlias(request.getWorkoutType());
        WorkoutTypePlugin plugin = workoutTypePlugins.getPlugin(workoutType);
        Integer duration = plugin.computeDuration(request);
        WorkoutLogEntity workoutLog = setWorkoutLogFields(new WorkoutLogEntity(), request, duration);
        workoutLog.setUid(userId);
        Long workoutLogId = fitnessRepository.createWorkout(workoutLog);
        plugin.handleCreateWorkout(request, workoutLogId);
    }

    @Override
    public WorkoutMetadata getWorkoutMetadata(String intensity, String workoutType) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        WorkoutMetadata metadata = fitnessRepository.getWorkoutMetadata(userId, intensity, workoutType);
        if (metadata != null) {
            return metadata;
        }
        metadata = new WorkoutMetadata(0, 0, 0);
        return metadata;
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
        WorkoutType workoutType = WorkoutType.getByAlias(request.getWorkoutType());
        WorkoutTypePlugin plugin = workoutTypePlugins.getPlugin(workoutType);
        Integer duration = plugin.computeDuration(request);
        WorkoutLogEntity workoutLog = setWorkoutLogFields(original, request, duration);
        workoutLog.setDateUpdated(new Date());
        fitnessRepository.updateWorkoutByPrimaryKey(workoutLog);
        fitnessRepository.deleteRunsByWorkoutId(request.getId());
        fitnessRepository.deleteStrengthTrainingByWorkoutId(request.getId());
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

    private Date combineDateAndTime(Date date, LocalTime time) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.of("Asia/Singapore");
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    @Override
    public void createScheduledWorkout(CreateScheduledWorkoutDTO request) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();

        WorkoutLogEntity workoutLog = new WorkoutLogEntity();
        workoutLog.setUid(userId);
        workoutLog.setDate(request.getDate());

        // Do not need to create push notification
        if (!request.getShouldSendReminder()) {
            return;
        }

        ScheduledEntity scheduled = new ScheduledEntity();
        scheduled.setTitle("Upcoming workout " + request.getTitle() + " scheduled at " + request.getTime());
        scheduled.setBody("You have a workout scheduled at " + request.getTime() + " today");
        scheduled.setScheduledTime(combineDateAndTime(request.getDate(), request.getTime()));
        scheduleRepository.createSchedule(scheduled);
    }
}
