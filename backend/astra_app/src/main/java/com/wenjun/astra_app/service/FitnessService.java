package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.vo.WorkoutPaginatedResponse;

public interface FitnessService {
    WorkoutPaginatedResponse getWorkouts(Long pageNo, Long pageSize) throws AstraException;
}
