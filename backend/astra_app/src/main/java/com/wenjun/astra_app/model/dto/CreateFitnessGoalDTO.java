package com.wenjun.astra_app.model.dto;

import lombok.Data;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class CreateFitnessGoalDTO {
    @NotEmpty(message = "Category cannot be empty")
    private final String category;
    private final String description;
    private final Date targetDate;
    @NotNull(message = "Target value cannot be null")
    @PositiveOrZero(message = "Target value must be equal to or greater than zero")
    private final Double targetValue;
    @NotEmpty(message = "Title cannot be null")
    private final String title;
}
