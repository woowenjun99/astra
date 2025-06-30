package com.wenjun.astra_persistence.models.manual;

public class WorkoutMetadata {
    private Integer totalWorkouts;

    private Integer totalDurationInSeconds;

    private Integer averageDurationInSeconds;

    public WorkoutMetadata(Integer totalWorkouts, Integer totalDurationInSeconds, Integer averageDurationInSeconds) {
        this.totalWorkouts = totalWorkouts;
        this.totalDurationInSeconds = totalDurationInSeconds;
        this.averageDurationInSeconds = averageDurationInSeconds;
    }

    public Integer getTotalWorkouts() {
        return totalWorkouts;
    }

    public void setTotalWorkouts(Integer totalWorkouts) {
        this.totalWorkouts = totalWorkouts;
    }

    public Integer getTotalDurationInSeconds() {
        return totalDurationInSeconds;
    }

    public void setTotalHours(Integer totalDurationInSeconds) {
        this.totalDurationInSeconds = totalDurationInSeconds;
    }

    public Integer getAverageDurationInSeconds() {
        return averageDurationInSeconds;
    }

    public void setAverageDuration(Integer averageDurationInSeconds) {
        this.averageDurationInSeconds = averageDurationInSeconds;
    }
}
