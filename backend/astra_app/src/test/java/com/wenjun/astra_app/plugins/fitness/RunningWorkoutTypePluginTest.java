package com.wenjun.astra_app.plugins.fitness;

import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;
import com.wenjun.astra_persistence.repository.FitnessRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class RunningWorkoutTypePluginTest {
    @Mock
    private FitnessRepository fitnessRepository;

    @Test
    public void getPlugin_shouldReturnRunning() {
        WorkoutTypePlugin runningWorkoutTypePlugin = new RunningWorkoutTypePlugin(fitnessRepository);
        Assertions.assertEquals(WorkoutType.RUNNING, runningWorkoutTypePlugin.getWorkoutType());
    }

    @Test
    public void computeDuration_shouldEqualToSumOfIndividualRuns() {
        WorkoutTypePlugin runningWorkoutTypePlugin = new RunningWorkoutTypePlugin(fitnessRepository);
        CreateWorkoutDTO request = new CreateWorkoutDTO();
        request.setDuration(2);
        request.setRuns(Collections.singletonList(new CreateWorkoutDTO.RunningDTO(1, 2)));
        Integer duration = runningWorkoutTypePlugin.computeDuration(request);
        Assertions.assertEquals(2, duration);
    }

    @Test
    public void handleCreateWorkout_runsNotEmpty_shouldCallFitnessRepository() {
        WorkoutTypePlugin runningWorkoutTypePlugin = new RunningWorkoutTypePlugin(fitnessRepository);
        CreateWorkoutDTO request = new CreateWorkoutDTO();
        request.setRuns(Collections.singletonList(new CreateWorkoutDTO.RunningDTO(1, 2)));
        Assertions.assertDoesNotThrow(() -> runningWorkoutTypePlugin.handleCreateWorkout(request, 1L));
        Mockito.verify(fitnessRepository, Mockito.times(1)).batchInsertRuns(Mockito.any());
        Mockito.verify(fitnessRepository, Mockito.never()).batchInsertStrengthTraining(Mockito.any());
    }
}
