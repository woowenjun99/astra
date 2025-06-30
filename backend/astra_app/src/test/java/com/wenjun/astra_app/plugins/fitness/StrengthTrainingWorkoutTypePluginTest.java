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
public class StrengthTrainingWorkoutTypePluginTest {
    @Mock
    private FitnessRepository fitnessRepository;

    @Test
    public void getPlugin_shouldReturnRunning() {
        WorkoutTypePlugin strengthTrainingWorkoutPlugin = new StrengthTrainingWorkoutPlugin(fitnessRepository);
        Assertions.assertEquals(WorkoutType.STRENGTH_TRAINING, strengthTrainingWorkoutPlugin.getWorkoutType());
    }

    @Test
    public void computeDuration_shouldEqualToSumOfIndividualRuns() {
        WorkoutTypePlugin strengthTrainingWorkoutPlugin = new StrengthTrainingWorkoutPlugin(fitnessRepository);
        CreateWorkoutDTO request = new CreateWorkoutDTO();
        request.setDuration(1);
        Integer duration = strengthTrainingWorkoutPlugin.computeDuration(request);
        Assertions.assertEquals(1, duration);
    }

    @Test
    public void handleCreateWorkout_runsNotEmpty_shouldCallFitnessRepository() {
        WorkoutTypePlugin strengthTrainingWorkoutPlugin = new StrengthTrainingWorkoutPlugin(fitnessRepository);
        CreateWorkoutDTO request = new CreateWorkoutDTO();
        request.setExercises(Collections.singletonList(new CreateWorkoutDTO.ExerciseDTO("sample", 2, 2, 2)));
        Assertions.assertDoesNotThrow(() -> strengthTrainingWorkoutPlugin.handleCreateWorkout(request, 1L));
        Mockito.verify(fitnessRepository, Mockito.times(1)).batchInsertStrengthTraining(Mockito.any());
        Mockito.verify(fitnessRepository, Mockito.never()).batchInsertRuns(Mockito.any());
    }
}
