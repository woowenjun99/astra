package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.service.FitnessService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/fitness")
public class FitnessController {
    private final FitnessService fitnessService;
}
