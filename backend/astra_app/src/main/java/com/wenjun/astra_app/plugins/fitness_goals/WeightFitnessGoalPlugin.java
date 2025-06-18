package com.wenjun.astra_app.plugins.fitness_goals;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.fitness_goals.FitnessGoalCategory;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.DailyLogEntity;
import com.wenjun.astra_persistence.repository.FitnessRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WeightFitnessGoalPlugin implements FitnessGoalPlugin {
    private final FitnessRepository fitnessRepository;

    /**
     * The enum of fitness goal category that the plguin belongs to
     *
     * @return The enum of fitness goal category that the plguin belongs to
     */
    @Override
    public FitnessGoalCategory getCategory() {
        return FitnessGoalCategory.WEIGHT;
    }

    /**
     * Retrieves the current progress of the user for the goal category.
     *
     * @return The current progress of the user.
     * @throws AstraException If the user is not logged in
     */
    @Override
    public Double getCurrentValue() throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        DailyLogEntity dailyLog = fitnessRepository.getRecentDailyLogsByUserId(userId, true);
        if (dailyLog == null) {
            return 0.0;
        }
        return dailyLog.getWeight();
    }

    /**
     * In order to track the progress, we need to have a benchmark (or a starting value)
     *
     * @return The starting value of the goal
     * @throws AstraException If the user is not logged in
     */
    @Override
    public Double getStartingValue() throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        DailyLogEntity dailyLog = fitnessRepository.getRecentDailyLogsByUserId(userId, false);
        if (dailyLog == null) {
            return 0.0;
        }
        return dailyLog.getWeight();
    }
}
