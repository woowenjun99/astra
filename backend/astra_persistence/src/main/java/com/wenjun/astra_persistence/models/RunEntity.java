package com.wenjun.astra_persistence.models;

import java.util.Date;

public class RunEntity {
    private Long id;

    private Date dateCreated;

    private Date dateUpdated;

    private Integer distance;

    private Integer duration;

    private Integer index;

    private Long workoutLogId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getWorkoutLogId() {
        return workoutLogId;
    }

    public void setWorkoutLogId(Long workoutLogId) {
        this.workoutLogId = workoutLogId;
    }
}
