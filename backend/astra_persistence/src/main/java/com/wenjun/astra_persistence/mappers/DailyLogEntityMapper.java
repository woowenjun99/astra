package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.DailyLogEntity;
import com.wenjun.astra_persistence.models.DailyLogEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DailyLogEntityMapper {
    long countByExample(DailyLogEntityExample example);

    int deleteByExample(DailyLogEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DailyLogEntity row);

    int insertSelective(DailyLogEntity row);

    List<DailyLogEntity> selectByExample(DailyLogEntityExample example);

    DailyLogEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") DailyLogEntity row, @Param("example") DailyLogEntityExample example);

    int updateByExample(@Param("row") DailyLogEntity row, @Param("example") DailyLogEntityExample example);

    int updateByPrimaryKeySelective(DailyLogEntity row);

    int updateByPrimaryKey(DailyLogEntity row);
}
