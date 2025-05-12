package com.wenjun.astra_persistence.repository;

import com.wenjun.astra_persistence.mappers.TransactionEntityMapper;
import com.wenjun.astra_persistence.models.TransactionEntity;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

@AllArgsConstructor
@Repository
public class TransactionRepository {
    @Resource
    private final TransactionEntityMapper transactionEntityMapper;

    public void createTransaction(TransactionEntity entity) {
        transactionEntityMapper.insertSelective(entity);
    }
}
