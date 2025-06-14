package com.wenjun.astra_app.plugins.fitness_goals;

import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;
import com.wenjun.astra_persistence.repository.FitnessRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WeightFitnessGoalPlugin implements FitnessGoalPlugin {
    private final FitnessRepository fitnessRepository;

    /**
     * @return
     */
    @Override
    public FitnessGoalCategory getCategory() {
        return FitnessGoalCategory.WEIGHT;
    }

    /**
     * @return
     */
    @Override
    public Double getCurrentValue() {
        return null;
    }
}
