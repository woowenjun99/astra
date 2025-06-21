package com.wenjun.astra_persistence.models.manual;

import lombok.Data;

import java.util.Date;

@Data
public class DailyActivity {
    final private Date date;
    final private Integer caloriesBurnt;

    DailyActivity(Date date, Integer caloriesBurnt) {
        this.date = date;
        this.caloriesBurnt = caloriesBurnt;
    }
}
