package com.wenjun.astra_app.plugins.fitness;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.fitness.WorkoutType;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class WorkoutTypePlugins {
    private final HashMap<WorkoutType, WorkoutTypePlugin> mappers;

    WorkoutTypePlugins(List<WorkoutTypePlugin> workoutTypePlugins) {
        mappers = new HashMap<>(workoutTypePlugins.size());
        workoutTypePlugins.forEach(workoutTypePlugin -> mappers.put(workoutTypePlugin.getWorkoutType(), workoutTypePlugin));
    }

    public WorkoutTypePlugin getPlugin(WorkoutType workoutType) throws AstraException {
        WorkoutTypePlugin plugin = mappers.getOrDefault(workoutType, null);
        if (plugin == null) {
            throw new AstraException(AstraExceptionEnum.INVALID_PARAMETERS, "Invalid workout type");
        }
        return plugin;
    }
}
