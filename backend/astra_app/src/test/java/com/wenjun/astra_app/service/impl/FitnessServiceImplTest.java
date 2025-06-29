package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;
import com.wenjun.astra_app.plugins.fitness.RunningWorkoutTypePlugin;
import com.wenjun.astra_app.plugins.fitness.WorkoutTypePlugins;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.repository.FitnessRepository;

import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class FitnessServiceImplTest {
    @Mock
    private FitnessRepository fitnessRepository;

    @Mock
    private WorkoutTypePlugins workoutTypePlugins;

    @Mock
    private RunningWorkoutTypePlugin runningWorkoutTypePlugin;

    @Test
    public void editWorkout_provideNullId_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(new AuthenticatedUser("userId"));
            FitnessService fitnessService = new FitnessServiceImpl(fitnessRepository, workoutTypePlugins);
            CreateWorkoutDTO request = new CreateWorkoutDTO();
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> fitnessService.editWorkout(request));
            Assertions.assertEquals("ID cannot be null", exception.getMessage());
        }
    }

    @Test
    public void editWorkout_invalidId_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(new AuthenticatedUser("userId"));
            Mockito.when(fitnessRepository.getWorkoutByUidAndId(1L, "userId")).thenReturn(null);
            FitnessService fitnessService = new FitnessServiceImpl(fitnessRepository, workoutTypePlugins);
            CreateWorkoutDTO request = new CreateWorkoutDTO();
            request.setId(1L);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> fitnessService.editWorkout(request));
            Assertions.assertEquals("workout with id 1 cannot be found", exception.getMessage());
        }
    }

    @Test
    public void editWorkout_validId_shouldCallCorrectly() throws AstraException {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(new AuthenticatedUser("userId"));
            Mockito.when(fitnessRepository.getWorkoutByUidAndId(1L, "userId")).thenReturn(new WorkoutLogEntity());
            Mockito.when(workoutTypePlugins.getPlugin(WorkoutType.RUNNING)).thenReturn(runningWorkoutTypePlugin);
            FitnessService fitnessService = new FitnessServiceImpl(fitnessRepository, workoutTypePlugins);
            CreateWorkoutDTO request = new CreateWorkoutDTO();
            request.setId(1L);
            request.setRuns(Collections.singletonList(new CreateWorkoutDTO.RunningDTO(1, 1)));
            request.setWorkoutType(WorkoutType.RUNNING.getAlias());

            // Act
            Assertions.assertDoesNotThrow(() -> fitnessService.editWorkout(request));

            // Assert
            Mockito.verify(runningWorkoutTypePlugin, Mockito.times(1)).handleCreateWorkout(request, 1L);
        }
    }
}
