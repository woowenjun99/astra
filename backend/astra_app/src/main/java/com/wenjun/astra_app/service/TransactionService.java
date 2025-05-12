package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.dto.CreateTransactionDTO;

public interface TransactionService {
    void createTransaction(CreateTransactionDTO request);
}
