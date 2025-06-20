package com.wenjun.astra_app.plugins.fitness_goals;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;

public interface FitnessGoalPlugin {
    /**
     * The enum of fitness goal category that the plguin belongs to
     *
     * @return The enum of fitness goal category that the plguin belongs to
     */
    FitnessGoalCategory getCategory();

    /**
     * Retrieves the current progress of the user for the goal category.
     *
     * @return The current progress of the user.
     * @throws AstraException If the user is not logged in
     */
    Double getCurrentValue() throws AstraException;

    /**
     * In order to track the progress, we need to have a benchmark (or a starting value)
     *
     * @return The starting value of the goal
     * @throws AstraException If the user is not logged in
     */
    Double getStartingValue() throws AstraException;
}
