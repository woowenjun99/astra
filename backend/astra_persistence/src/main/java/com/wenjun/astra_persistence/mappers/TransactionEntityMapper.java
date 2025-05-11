package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.TransactionEntity;
import com.wenjun.astra_persistence.models.TransactionEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionEntityMapper {
    long countByExample(TransactionEntityExample example);

    int deleteByExample(TransactionEntityExample example);

    int deleteByPrimaryKey(Object id);

    int insert(TransactionEntity row);

    int insertSelective(TransactionEntity row);

    List<TransactionEntity> selectByExample(TransactionEntityExample example);

    TransactionEntity selectByPrimaryKey(Object id);

    int updateByExampleSelective(@Param("row") TransactionEntity row, @Param("example") TransactionEntityExample example);

    int updateByExample(@Param("row") TransactionEntity row, @Param("example") TransactionEntityExample example);

    int updateByPrimaryKeySelective(TransactionEntity row);

    int updateByPrimaryKey(TransactionEntity row);
}
