package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.dto.CreateTransactionDTO;
import com.wenjun.astra_app.service.TransactionService;
import com.wenjun.astra_persistence.models.TransactionEntity;
import com.wenjun.astra_persistence.repository.TransactionRepository;

import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public void createTransaction(CreateTransactionDTO request) {
        TransactionEntity entity = new TransactionEntity();
        FirebaseToken token = ThreadLocalUser.get();
        entity.setUserId(token.getUid());
        entity.setName(request.getName());
        entity.setTransactionType(request.getTransactionType());
        transactionRepository.createTransaction(entity);
    }
}
