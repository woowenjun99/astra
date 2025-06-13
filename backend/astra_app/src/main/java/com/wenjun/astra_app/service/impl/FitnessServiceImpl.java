package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.service.FitnessService;
import com.wenjun.astra_persistence.repository.FitnessRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FitnessServiceImpl implements FitnessService {
    private final FitnessRepository fitnessRepository;
}
