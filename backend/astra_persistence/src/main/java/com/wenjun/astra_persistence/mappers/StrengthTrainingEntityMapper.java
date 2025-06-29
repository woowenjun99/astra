package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.StrengthTrainingEntity;
import com.wenjun.astra_persistence.models.StrengthTrainingEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StrengthTrainingEntityMapper {
    long countByExample(StrengthTrainingEntityExample example);

    int deleteByExample(StrengthTrainingEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StrengthTrainingEntity row);

    int insertSelective(StrengthTrainingEntity row);

    List<StrengthTrainingEntity> selectByExample(StrengthTrainingEntityExample example);

    StrengthTrainingEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") StrengthTrainingEntity row, @Param("example") StrengthTrainingEntityExample example);

    int updateByExample(@Param("row") StrengthTrainingEntity row, @Param("example") StrengthTrainingEntityExample example);

    int updateByPrimaryKeySelective(StrengthTrainingEntity row);

    int updateByPrimaryKey(StrengthTrainingEntity row);
}
