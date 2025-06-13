package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateFitnessGoalDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.FitnessGoalEntity;
import com.wenjun.astra_persistence.repository.FitnessRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;

    /**
     * Create a fitness goal for the user.
     *
     * @param request The payload passed from the frontend
     * @throws AstraException If the user is not logged in
     * @throws AstraException If the fitness goal category is invalid
     * @throws AstraException If the fitness goal already exist for the user
     */
    @Override
    public void createFitnessGoal(CreateFitnessGoalDTO request) throws AstraException {
        FitnessGoalCategory fitnessGoalCategory = FitnessGoalCategory.getByAlias(request.getCategory());
        AuthenticatedUser user = ThreadLocalUser.get();
        if (user == null) {
            throw new AstraException(AstraExceptionEnum.UNAUTHORIZED);
        }
        FitnessGoalEntity existingGoal = fitnessRepository.getByPrimaryKey(fitnessGoalCategory.getAlias(), user.getUid());
        if (existingGoal != null) {
            throw new AstraException(AstraExceptionEnum.CONFLICT, "fitness goal for the user");
        }
        FitnessGoalEntity entity = new FitnessGoalEntity();
        entity.setUid(user.getUid());
        entity.setCategory(fitnessGoalCategory.getAlias());
        entity.setTargetValue(request.getTargetValue());
        entity.setTargetDate(request.getTargetDate());
        entity.setTargetValue(request.getTargetValue());
        fitnessRepository.insertFitnessGoalSelective(entity);
    }
}
