package com.wenjun.astra_app.model.vo;

import com.wenjun.astra_persistence.models.ExerciseEntity;
import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetWorkoutVO {
    private WorkoutLogEntity workout;
    private List<RunEntity> runs;
    private List<ExerciseEntity> exercises;
}
