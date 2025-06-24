package com.wenjun.astra_persistence.models.manual;

import lombok.Data;

@Data
public class WorkoutMetadata {
    private final Integer totalWorkouts;

    private final Integer totalHours;

    private final Integer averageDuration;

    WorkoutMetadata(Integer totalWorkouts, Integer totalHours, Integer averageDuration) {
        this.totalWorkouts = totalWorkouts;
        this.totalHours = totalHours;
        this.averageDuration = averageDuration;
    }
}
