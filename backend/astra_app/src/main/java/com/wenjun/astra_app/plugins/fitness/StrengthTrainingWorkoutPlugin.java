package com.wenjun.astra_app.plugins.fitness;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;
import com.wenjun.astra_persistence.models.StrengthTrainingEntity;
import com.wenjun.astra_persistence.repository.FitnessRepository;

import lombok.AllArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class StrengthTrainingWorkoutPlugin implements WorkoutTypePlugin {
    private final FitnessRepository fitnessRepository;

    @Override
    public WorkoutType getWorkoutType() {
        return WorkoutType.STRENGTH_TRAINING;
    }

    @Override
    public void handleCreateWorkout(CreateWorkoutDTO request, Long workoutLogId) throws AstraException {
        if (CollectionUtils.isEmpty(request.getExercises())) {
            return;
        }
        List<StrengthTrainingEntity> exercises = new ArrayList<>(request.getExercises().size());
        for (int i = 0; i < request.getExercises().size(); ++i) {
            CreateWorkoutDTO.ExerciseDTO entity = request.getExercises().get(i);
            StrengthTrainingEntity exercise = new StrengthTrainingEntity();
            exercise.setName(entity.getName());
            exercise.setReps(entity.getReps());
            exercise.setSets(entity.getSets());
            exercise.setWorkoutLogId(workoutLogId);
            exercise.setWeight(entity.getWeight());
            exercise.setIndex(i);
            exercises.add(exercise);
        }
        if (CollectionUtils.isNotEmpty(exercises)) {
            fitnessRepository.batchInsertStrengthTraining(exercises);
        }
    }
}
