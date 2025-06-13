package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.FitnessGoalEntity;
import com.wenjun.astra_persistence.models.FitnessGoalEntityExample;
import com.wenjun.astra_persistence.models.FitnessGoalEntityKey;

import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface FitnessGoalEntityMapper {
    long countByExample(FitnessGoalEntityExample example);

    int deleteByExample(FitnessGoalEntityExample example);

    int deleteByPrimaryKey(FitnessGoalEntityKey key);

    int insert(FitnessGoalEntity row);

    int insertSelective(FitnessGoalEntity row);

    List<FitnessGoalEntity> selectByExample(FitnessGoalEntityExample example);

    FitnessGoalEntity selectByPrimaryKey(FitnessGoalEntityKey key);

    int updateByExampleSelective(@Param("row") FitnessGoalEntity row, @Param("example") FitnessGoalEntityExample example);

    int updateByExample(@Param("row") FitnessGoalEntity row, @Param("example") FitnessGoalEntityExample example);

    int updateByPrimaryKeySelective(FitnessGoalEntity row);

    int updateByPrimaryKey(FitnessGoalEntity row);
}
