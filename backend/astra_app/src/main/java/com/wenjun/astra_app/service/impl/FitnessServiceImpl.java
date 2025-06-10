package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.vo.WorkoutPaginatedResponse;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.repository.FitnessRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;

    @Override
    public WorkoutPaginatedResponse getWorkouts(Long pageNo, Long pageSize) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.get();
        if (authenticatedUser == null) {
            throw new AstraException(AstraExceptionEnum.UNAUTHORIZED);
        }
        String userId = authenticatedUser.getUid();
        List<WorkoutLogEntity> workoutLogs = fitnessRepository.getWorkoutLogEntity(userId, pageSize, pageNo - 1);
        Long total = fitnessRepository.getTotalWorkoutLogEntityCount(userId);
        return WorkoutPaginatedResponse
                .builder()
                .total(total)
                .workoutLogs(workoutLogs)
                .build();
    }
}
