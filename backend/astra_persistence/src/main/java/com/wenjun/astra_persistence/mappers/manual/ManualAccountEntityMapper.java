package com.wenjun.astra_persistence.mappers.manual;

import org.apache.ibatis.annotations.Param;

public interface ManualAccountEntityMapper {
    Long countByEmailAndPasswordProvider(@Param("email") String email);
}
