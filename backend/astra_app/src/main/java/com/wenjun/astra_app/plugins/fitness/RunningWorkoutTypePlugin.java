package com.wenjun.astra_app.plugins.fitness;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;
import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.repository.FitnessRepository;

import lombok.AllArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RunningWorkoutTypePlugin implements WorkoutTypePlugin {
    private final FitnessRepository fitnessRepository;

    @Override
    public WorkoutType getWorkoutType() {
        return WorkoutType.RUNNING;
    }

    @Override
    public void handleCreateWorkout(CreateWorkoutDTO request, Long workoutLogId) throws AstraException {
        if (CollectionUtils.isEmpty(request.getRuns())) {
            return;
        }

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
    }

    @Override
    public Integer computeDuration(CreateWorkoutDTO request) {
        Integer duration = 0;
        for (CreateWorkoutDTO.RunningDTO run: request.getRuns()) {
            duration += run.getDuration();
        }
        return duration;
    }
}
