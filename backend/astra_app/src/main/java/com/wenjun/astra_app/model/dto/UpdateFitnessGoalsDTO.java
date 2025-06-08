package com.wenjun.astra_app.model.dto;

import lombok.Data;

import java.util.Date;

import jakarta.validation.constraints.Positive;

@Data
public class UpdateFitnessGoalsDTO {
    private Date goalDate;

    @Positive
    private Double goalWeight;
}
