package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.FitnessGoalEntityMapper;
import com.wenjun.astra_persistence.mappers.manual.ManualDailyLogEntityMapper;
import com.wenjun.astra_persistence.models.DailyLogEntity;
import com.wenjun.astra_persistence.models.FitnessGoalEntity;
import com.wenjun.astra_persistence.models.FitnessGoalEntityExample;
import com.wenjun.astra_persistence.models.FitnessGoalEntityKey;

import org.springframework.stereotype.Repository;

import java.util.List;

import jakarta.annotation.Resource;

@Repository
public class FitnessRepository {
    @Resource
    private FitnessGoalEntityMapper fitnessGoalEntityMapper;

    @Resource
    private ManualDailyLogEntityMapper manualDailyLogEntityMapper;

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

    public DailyLogEntity getRecentDailyLogsByUserId(String uid, Boolean isDescending) {
        return manualDailyLogEntityMapper.getMostRecentDailyLog(uid, isDescending);
    }

    public void deleteFitnessGoal(String userId, String category) {
        FitnessGoalEntityKey primaryKey = new FitnessGoalEntityKey();
        primaryKey.setUid(userId);
        primaryKey.setCategory(category);
        fitnessGoalEntityMapper.deleteByPrimaryKey(primaryKey);
    }
}
