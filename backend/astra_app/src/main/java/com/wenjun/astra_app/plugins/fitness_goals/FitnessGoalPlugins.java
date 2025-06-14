package com.wenjun.astra_app.plugins.fitness_goals;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class FitnessGoalPlugins {
    private final HashMap<FitnessGoalCategory, FitnessGoalPlugin> plugins;

    FitnessGoalPlugins(List<FitnessGoalPlugin> fitnessGoalPlugins) {
        plugins = new HashMap<>(fitnessGoalPlugins.size());
        fitnessGoalPlugins.forEach(fitnessGoalPlugin -> {
            plugins.put(fitnessGoalPlugin.getCategory(), fitnessGoalPlugin);
        });
    }

    public FitnessGoalPlugin getPlugin(FitnessGoalCategory fitnessGoalCategory) throws AstraException {
        if (!plugins.containsKey(fitnessGoalCategory)) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Unsupported fitness category");
        }
        return plugins.get(fitnessGoalCategory);
    }
}
