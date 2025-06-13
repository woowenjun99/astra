package com.wenjun.astra_app.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class FitnessGoal {
    private String description;

    private Date targetDate;

    private Double targetValue;

    private String title;

    private Double currentValue;

    private String category;
}
