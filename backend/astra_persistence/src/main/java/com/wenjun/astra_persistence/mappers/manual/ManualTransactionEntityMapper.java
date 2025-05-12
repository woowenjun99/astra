package com.wenjun.astra_persistence.mappers.manual;

import com.wenjun.astra_persistence.models.manual.CategoryWithSpending;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ManualTransactionEntityMapper {
    List<CategoryWithSpending> getSpendingByCategory(
            @Param("userId") String userId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}
