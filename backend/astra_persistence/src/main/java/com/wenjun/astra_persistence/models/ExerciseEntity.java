package com.wenjun.astra_persistence.models;

import java.util.Date;

public class ExerciseEntity {
    private Long id;

    private Date dateCreated;

    private Date dateUpdated;

    private Integer index;

    private String name;

    private Integer reps;

    private Integer sets;

    private Integer weight;

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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getWorkoutLogId() {
        return workoutLogId;
    }

    public void setWorkoutLogId(Long workoutLogId) {
        this.workoutLogId = workoutLogId;
    }
}
