package com.wenjun.astra_app.model.vo;

import com.wenjun.astra_persistence.models.WorkoutLogEntity;

import lombok.Data;

import java.util.List;

@Data
public class GetWorkoutVO {
    private List<WorkoutLogEntity> workouts;
    private Long total;
}
