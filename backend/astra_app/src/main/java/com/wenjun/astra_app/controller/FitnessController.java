package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateWorkoutDTO;
import com.wenjun.astra_app.model.vo.GetWorkoutVO;
import com.wenjun.astra_app.model.vo.GetWorkoutsVO;
import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_persistence.models.manual.WorkoutMetadata;

import lombok.AllArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/fitness")
public class FitnessController {
    private final FitnessService fitnessService;

    @GetMapping("/workouts")
    public GetWorkoutsVO getWorkouts(
            @RequestParam("pageSize") Long pageSize,
            @RequestParam("pageNo") Long pageNo,
            @RequestParam(value = "workoutType", required = false) String workoutType,
            @RequestParam(value = "intensity", required = false) String intensity
    ) throws AstraException {
        return fitnessService.getWorkouts(pageSize, pageNo, workoutType, intensity);
    }

    @DeleteMapping("/workouts/{id}")
    public void deleteWorkout(@PathVariable("id") Long id) throws AstraException {
        fitnessService.deleteWorkout(id);
    }

    @Transactional
    @PostMapping("/workouts")
    public void createWorkout(@RequestBody @Valid CreateWorkoutDTO request) throws AstraException {
        fitnessService.createWorkout(request);
    }

    @Transactional
    @PutMapping("/workouts")
    public void editWorkout(@RequestBody @Valid CreateWorkoutDTO request) throws AstraException {
        fitnessService.editWorkout(request);
    }

    @GetMapping("/workouts/{id}")
    public GetWorkoutVO getWorkout(@PathVariable("id") Long id) throws AstraException {
        return fitnessService.getWorkout(id);
    }

    @GetMapping("/workouts/metadata")
    public WorkoutMetadata getWorkoutMetadata() throws AstraException {
        return fitnessService.getWorkoutMetadata();
    }
}
