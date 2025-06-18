package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateFitnessGoalDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;
import com.wenjun.astra_app.model.vo.FitnessGoal;
import com.wenjun.astra_app.plugins.fitness_goals.FitnessGoalPlugin;
import com.wenjun.astra_app.plugins.fitness_goals.FitnessGoalPlugins;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.FitnessGoalEntity;
import com.wenjun.astra_persistence.repository.FitnessRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;
    private final FitnessGoalPlugins fitnessGoalPlugins;

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
        AuthenticatedUser user = ThreadLocalUser.getAuthenticatedUser();
        FitnessGoalEntity existingGoal = fitnessRepository.getByPrimaryKey(fitnessGoalCategory.getAlias(), user.getUid());
        if (existingGoal != null) {
            throw new AstraException(AstraExceptionEnum.CONFLICT, "fitness goal for the user");
        }
        FitnessGoalEntity entity = new FitnessGoalEntity();
        entity.setUid(user.getUid());
        entity.setTitle(request.getTitle());
        entity.setCategory(fitnessGoalCategory.getAlias());
        entity.setTargetValue(request.getTargetValue());
        entity.setTargetDate(request.getTargetDate());
        entity.setTargetValue(request.getTargetValue());
        fitnessRepository.insertFitnessGoalSelective(entity);
    }

    private FitnessGoal convertToFitnessGoal(FitnessGoalEntity fitnessGoalEntity) throws AstraException {
        FitnessGoalCategory category = FitnessGoalCategory.getByAlias(fitnessGoalEntity.getCategory());
        FitnessGoalPlugin plugin = fitnessGoalPlugins.getPlugin(category);
        Double currentValue = plugin.getCurrentValue();
        FitnessGoal fitnessGoal = FitnessGoal
                .builder()
                .category(category.getAlias())
                .currentValue(currentValue)
                .description(fitnessGoalEntity.getDescription())
                .startingValue(plugin.getStartingValue())
                .targetDate(fitnessGoalEntity.getTargetDate())
                .targetValue(fitnessGoalEntity.getTargetValue())
                .title(fitnessGoalEntity.getTitle())
                .unit(category.getUnit())
                .build();
        return fitnessGoal;
    }

    /**
     * Get all the fitness goals for the user
     *
     * @return a list of fitness goals
     * @throws AstraException If the user is not logged in
     */
    @Override
    public List<FitnessGoal> getFitnessGoals() throws AstraException {
        AuthenticatedUser user = ThreadLocalUser.getAuthenticatedUser();
        List<FitnessGoalEntity> fitnessGoals = fitnessRepository.getFitnessGoalsByUid(user.getUid());
        List<FitnessGoal> response = new ArrayList<>();
        for (FitnessGoalEntity fitnessGoalEntity: fitnessGoals) {
            FitnessGoal fitnessGoal = convertToFitnessGoal(fitnessGoalEntity);
            response.add(fitnessGoal);
        }
        return response;
    }

    @Override
    public void deleteFitnessGoal(String category) throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        FitnessGoalCategory fitnessGoalCategory = FitnessGoalCategory.getByAlias(category);
        fitnessRepository.deleteFitnessGoal(userId, fitnessGoalCategory.getAlias());
    }
}
