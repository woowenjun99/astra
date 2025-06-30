package com.wenjun.astra_app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@Data
public class CreateWorkoutDTO {
    @PositiveOrZero
    private Integer caloriesBurnt;

    @NotNull
    private Date date;

    @NotNull
    @PositiveOrZero
    private Integer duration;

    @NotNull
    private List<@NotNull ExerciseDTO> exercises;

    @NotNull
    @NotEmpty
    private String intensity;

    private String remarks;

    @NotNull
    private List<@NotNull RunningDTO> runs;

    @NotEmpty
    @NotNull
    private String title;

    @NotEmpty
    @NotNull
    private String workoutType;

    private Long id;

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
