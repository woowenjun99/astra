package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.FitnessGoalEntityMapper;
import com.wenjun.astra_persistence.models.FitnessGoalEntity;
import com.wenjun.astra_persistence.models.FitnessGoalEntityKey;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class FitnessRepository {
    private FitnessGoalEntityMapper fitnessGoalEntityMapper;

    public void insertFitnessGoalSelective(FitnessGoalEntity fitnessGoalEntity) {
        fitnessGoalEntityMapper.insertSelective(fitnessGoalEntity);
    }

    public FitnessGoalEntity getByPrimaryKey(String category, String uid) {
        FitnessGoalEntityKey primaryKey = new FitnessGoalEntityKey();
        primaryKey.setUid(uid);
        primaryKey.setCategory(category);
        return fitnessGoalEntityMapper.selectByPrimaryKey(primaryKey);
    }
}
