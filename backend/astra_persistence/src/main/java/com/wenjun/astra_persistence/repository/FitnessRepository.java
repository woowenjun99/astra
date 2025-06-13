package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.FitnessGoalEntityMapper;
import com.wenjun.astra_persistence.models.FitnessGoalEntity;
import com.wenjun.astra_persistence.models.FitnessGoalEntityExample;
import com.wenjun.astra_persistence.models.FitnessGoalEntityKey;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<FitnessGoalEntity> getFitnessGoalsByUid(String uid) {
        FitnessGoalEntityExample example = new FitnessGoalEntityExample();
        example.createCriteria().andUidEqualTo(uid);
        return fitnessGoalEntityMapper.selectByExample(example);
    }
}
