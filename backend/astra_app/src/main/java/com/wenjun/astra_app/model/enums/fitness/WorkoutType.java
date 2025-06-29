package com.wenjun.astra_app.model.enums.fitness;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum WorkoutType {
    RUNNING("Running"),
    STRENGTH_TRAINING("Strength Training");

    private final String alias;
    private static final HashMap<String, WorkoutType> mappers;

    static {
        mappers = new HashMap<>();
        for (WorkoutType workoutType: WorkoutType.values()) {
            mappers.put(workoutType.alias, workoutType);
        }
    }

    WorkoutType(String alias) {
        this.alias = alias;
    }

    public static WorkoutType getByAlias(String alias) throws AstraException {
        WorkoutType workoutType = mappers.getOrDefault(alias, null);
        if (workoutType == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Invalid workout type");
        }
        return workoutType;
    }
}
