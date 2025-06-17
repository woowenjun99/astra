package com.wenjun.astra_persistence.mappers.manual;

import com.wenjun.astra_persistence.models.DailyLogEntity;

import org.apache.ibatis.annotations.Param;

public interface ManualDailyLogEntityMapper {
    /**
     * Get the most recent daily log created by the user
     *
     * @param userId The user id
     * @return The daily log if exist.
     */
    DailyLogEntity getMostRecentDailyLog(@Param("userId") String userId, @Param("isDescending") Boolean isDescending);
}
