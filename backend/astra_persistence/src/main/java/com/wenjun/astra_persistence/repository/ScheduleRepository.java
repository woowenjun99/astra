package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.ScheduledEntityMapper;

import com.wenjun.astra_persistence.models.ScheduledEntity;
import com.wenjun.astra_persistence.models.ScheduledEntityExample;

import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.annotation.Resource;

@Repository
public class ScheduleRepository {
    @Resource
    private ScheduledEntityMapper scheduledEntityMapper;

    public List<ScheduledEntity> getAllScheduledActivities() {
        ScheduledEntityExample example = new ScheduledEntityExample();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 29);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateAt29 = calendar.getTime();

        calendar.set(Calendar.MINUTE, 31);
        Date dateAt31 = calendar.getTime();

        example.createCriteria().andScheduledTimeBetween(dateAt29, dateAt31);
        return scheduledEntityMapper.selectByExample(example);
    }
}
