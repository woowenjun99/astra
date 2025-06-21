package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateFitnessGoalDTO;
import com.wenjun.astra_app.model.vo.FitnessGoal;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.DailyActivity;

import java.util.List;

public interface FitnessService {
    /**
     * Create a fitness goal for the user.
     *
     * @param request The payload passed from the frontend
     * @throws AstraException If the user is not logged in
     * @throws AstraException If the fitness goal category is invalid
     * @throws AstraException If the fitness goal already exist for the user
     */
    void createFitnessGoal(CreateFitnessGoalDTO request) throws AstraException;

    /**
     * Get all the fitness goals for the user
     *
     * @return a list of fitness goals
     */
    List<FitnessGoal> getFitnessGoals() throws AstraException;

    void deleteFitnessGoal(String category) throws AstraException;

    /**
     * Get the top 3 most recent workouts by the user
     *
     * @return An array of workouts
     * @throws AstraException
     */
    List<WorkoutLogEntity> getRecentWorkouts() throws AstraException;

    List<DailyActivity> getWeeklyActivity() throws AstraException;
}
