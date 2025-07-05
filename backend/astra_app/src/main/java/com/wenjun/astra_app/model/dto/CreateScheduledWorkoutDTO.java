package com.wenjun.astra_app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
public class CreateScheduledWorkoutDTO {
    @NotNull
    @Future
    private final Date date;

    @Pattern(regexp = "^(Daily|Weekly|Every 2 weeks|Monthly)")
    private String frequency;

    @NotNull
    private final Boolean isRecurring;

    private final String remarks;

    @NotNull
    private final Boolean shouldSendReminder;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime time;

    @NotNull
    @NotEmpty
    private final String title;

    @NotNull
    @Pattern(regexp = "^(Strength Training|Running)$")
    private final String workoutType;
}
