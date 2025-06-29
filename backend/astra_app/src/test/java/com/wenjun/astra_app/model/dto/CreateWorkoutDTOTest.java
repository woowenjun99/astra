package com.wenjun.astra_app.model.dto;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CreateWorkoutDTOTest {
    @Test
    public void getDurationToSave_workoutTypeIsRunning_useRunningDuration() throws AstraException {
        CreateWorkoutDTO request = new CreateWorkoutDTO();
        request.setWorkoutType(WorkoutType.RUNNING.getAlias());
        request.setRuns(Collections.singletonList(new CreateWorkoutDTO.RunningDTO(1000, 2)));
        request.setDuration(1);
        Assertions.assertEquals(2, request.getDurationToSave());
    }

    @Test
    public void getDurationToSave_workoutTypeIsNotRunning_useDurationParam() throws AstraException {
        CreateWorkoutDTO request = new CreateWorkoutDTO();
        request.setWorkoutType(WorkoutType.STRENGTH_TRAINING.getAlias());
        request.setExercises(Collections.singletonList(new CreateWorkoutDTO.ExerciseDTO("sample", 2, 2, 2)));
        request.setDuration(1);
        Assertions.assertEquals(1, request.getDurationToSave());
    }
}
