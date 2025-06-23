package com.wenjun.astra_app.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class CreateWorkoutDTO {
    @PositiveOrZero
    private final Integer caloriesBurnt;

    @NotNull
    private final Date date;

    @NotNull
    @PositiveOrZero
    private final Integer duration;

    @NotNull
    private final List<@NotNull ExerciseDTO> exercises;

    @NotNull
    @NotEmpty
    private final String intensity;

    private final String remarks;

    @NotNull
    private final List<@NotNull RunningDTO> runs;

    @NotEmpty
    @NotNull
    private final String title;

    @NotEmpty
    @NotNull
    private final String workoutType;

    @Data
    public static class ExerciseDTO {
        @NotNull
        @NotEmpty
        private final String name;

        @NotNull
        @PositiveOrZero
        private final Integer reps;

        @NotNull
        @PositiveOrZero
        private final Integer sets;

        @NotNull
        @PositiveOrZero
        private final Integer weight;
    }

    @Data
    public static class RunningDTO {
        @NotNull
        @PositiveOrZero
        private final Integer distance;

        @NotNull
        @PositiveOrZero
        private final Integer duration;
    }
}
