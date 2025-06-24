package com.wenjun.astra_persistence.models.manual;

public class WorkoutMetadata {
    private Integer totalWorkouts;

    private Integer totalHours;

    private Integer averageDuration;

    public WorkoutMetadata(Integer totalWorkouts, Integer totalHours, Integer averageDuration) {
        this.totalWorkouts = totalWorkouts;
        this.totalHours = totalHours;
        this.averageDuration = averageDuration;
    }

    public Integer getTotalWorkouts() {
        return totalWorkouts;
    }

    public void setTotalWorkouts(Integer totalWorkouts) {
        this.totalWorkouts = totalWorkouts;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(Integer averageDuration) {
        this.averageDuration = averageDuration;
    }
}
