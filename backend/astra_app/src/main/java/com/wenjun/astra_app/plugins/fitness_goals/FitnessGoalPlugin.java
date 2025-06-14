package com.wenjun.astra_app.plugins.fitness_goals;

import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;

public interface FitnessGoalPlugin {
    FitnessGoalCategory getCategory();
    Double getCurrentValue();
}
