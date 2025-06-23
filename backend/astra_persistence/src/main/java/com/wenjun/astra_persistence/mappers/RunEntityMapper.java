package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.RunEntity;
import com.wenjun.astra_persistence.models.RunEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RunEntityMapper {
    long countByExample(RunEntityExample example);

    int deleteByExample(RunEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RunEntity row);

    int insertSelective(RunEntity row);

    List<RunEntity> selectByExample(RunEntityExample example);

    RunEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RunEntity row, @Param("example") RunEntityExample example);

    int updateByExample(@Param("row") RunEntity row, @Param("example") RunEntityExample example);

    int updateByPrimaryKeySelective(RunEntity row);

    int updateByPrimaryKey(RunEntity row);
}
