package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.ExerciseEntity;
import com.wenjun.astra_persistence.models.ExerciseEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface ExerciseEntityMapper {
    long countByExample(ExerciseEntityExample example);

    int deleteByExample(ExerciseEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExerciseEntity row);

    int insertSelective(ExerciseEntity row);

    List<ExerciseEntity> selectByExample(ExerciseEntityExample example);

    ExerciseEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") ExerciseEntity row, @Param("example") ExerciseEntityExample example);

    int updateByExample(@Param("row") ExerciseEntity row, @Param("example") ExerciseEntityExample example);

    int updateByPrimaryKeySelective(ExerciseEntity row);

    int updateByPrimaryKey(ExerciseEntity row);
}
