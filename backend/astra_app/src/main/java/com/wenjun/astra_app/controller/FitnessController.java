package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.vo.WorkoutPaginatedResponse;
import com.wenjun.astra_app.service.FitnessService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/fitness")
public class FitnessController {
    private final FitnessService fitnessService;

    @GetMapping
    private WorkoutPaginatedResponse getWorkouts(
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long pageSize
    ) throws AstraException {
        return fitnessService.getWorkouts(pageNo, pageSize);
    }
}
