package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.FitnessLogEntity;
import com.wenjun.astra_persistence.models.FitnessLogEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FitnessLogEntityMapper {
    long countByExample(FitnessLogEntityExample example);

    int deleteByExample(FitnessLogEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FitnessLogEntity row);

    int insertSelective(FitnessLogEntity row);

    List<FitnessLogEntity> selectByExample(FitnessLogEntityExample example);

    FitnessLogEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") FitnessLogEntity row, @Param("example") FitnessLogEntityExample example);

    int updateByExample(@Param("row") FitnessLogEntity row, @Param("example") FitnessLogEntityExample example);

    int updateByPrimaryKeySelective(FitnessLogEntity row);

    int updateByPrimaryKey(FitnessLogEntity row);
}
