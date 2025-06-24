package com.wenjun.astra_persistence.models.manual;

import java.util.Date;

public class DailyActivity {
    private Date date;
    private Integer caloriesBurnt;

    DailyActivity(Date date, Integer caloriesBurnt) {
        this.date = date;
        this.caloriesBurnt = caloriesBurnt;
    }

    public Date getDate() {
        return date;
    }

    public Integer getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCaloriesBurnt(Integer caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }
}
