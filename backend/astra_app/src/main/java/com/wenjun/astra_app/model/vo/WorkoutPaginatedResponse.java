package com.wenjun.astra_app.model.vo;

import com.wenjun.astra_persistence.models.WorkoutLogEntity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WorkoutPaginatedResponse {
    private final Long total;
    private final List<WorkoutLogEntity> workoutLogs;
}
