package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateFitnessGoalDTO;
import com.wenjun.astra_app.model.vo.FitnessGoal;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_persistence.models.WorkoutLogEntity;
import com.wenjun.astra_persistence.models.manual.DailyActivity;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/fitness")
public class FitnessController {
    private final FitnessService fitnessService;

    @PostMapping("/goals")
    public void createFitnessGoal(@RequestBody @Valid CreateFitnessGoalDTO request) throws AstraException {
        fitnessService.createFitnessGoal(request);
    }

    @GetMapping("/goals")
    public List<FitnessGoal> getFitnessGoals() throws AstraException {
        return fitnessService.getFitnessGoals();
    }

    @GetMapping("/workouts/recent")
    public List<WorkoutLogEntity> getRecentWorkouts() throws AstraException {
        return fitnessService.getRecentWorkouts();
    }

    @GetMapping("/weekly-activity")
    public List<DailyActivity> getWeeklyActivity() throws AstraException {
        return fitnessService.getWeeklyActivity();
    }
}
