package com.wenjun.astra_app.plugins.fitness;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;

public interface WorkoutTypePlugin {
    WorkoutType getWorkoutType();

    void handleCreateWorkout(CreateWorkoutDTO request, Long workoutLogId) throws AstraException;

    Integer computeDuration(CreateWorkoutDTO request);
}
