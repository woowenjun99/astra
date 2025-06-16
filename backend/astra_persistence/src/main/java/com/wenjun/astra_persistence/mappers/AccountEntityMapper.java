package com.wenjun.astra_persistence.mappers;

import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.models.AccountEntityExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface AccountEntityMapper {
    long countByExample(AccountEntityExample example);

    int deleteByExample(AccountEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AccountEntity row);

    int insertSelective(AccountEntity row);

    List<AccountEntity> selectByExample(AccountEntityExample example);

    AccountEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AccountEntity row, @Param("example") AccountEntityExample example);

    int updateByExample(@Param("row") AccountEntity row, @Param("example") AccountEntityExample example);

    int updateByPrimaryKeySelective(AccountEntity row);

    int updateByPrimaryKey(AccountEntity row);
}
