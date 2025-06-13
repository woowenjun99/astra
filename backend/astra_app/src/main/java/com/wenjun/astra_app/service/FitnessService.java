package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateFitnessGoalDTO;

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
}
