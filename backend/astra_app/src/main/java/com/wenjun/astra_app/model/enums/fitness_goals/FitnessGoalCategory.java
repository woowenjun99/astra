package com.wenjun.astra_app.model.enums.fitness_goals;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum FitnessGoalCategory {
    DISTANCE("Distance", "Km"),
    FREQUENCY("Frequency", ""),
    WEIGHT("Weight", "Kg");

    private final String alias;
    private final String unit;
    private static HashMap<String, FitnessGoalCategory> mappers;

    FitnessGoalCategory(String alias, String unit) {
        this.alias = alias;
        this.unit = unit;
    }

    static {
        mappers = new HashMap<>();
        for (FitnessGoalCategory fitnessGoalCategory: FitnessGoalCategory.values()) {
            mappers.put(fitnessGoalCategory.getAlias(), fitnessGoalCategory);
        }
    }

    public static FitnessGoalCategory getByAlias(String alias) throws AstraException {
        FitnessGoalCategory fitnessGoalCategory = mappers.getOrDefault(alias, null);
        if (fitnessGoalCategory == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Invalid fitness goal category");
        }
        return fitnessGoalCategory;
    }
}
