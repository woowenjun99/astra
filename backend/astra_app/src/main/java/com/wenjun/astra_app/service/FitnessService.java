package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateFitnessGoalDTO;

public interface FitnessService {
    /**
     * Create a fitness goal for the user.
     */
    void createFitnessGoal(CreateFitnessGoalDTO request) throws AstraException;
}
