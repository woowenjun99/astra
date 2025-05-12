package com.wenjun.astra_app.model.dto;

import lombok.Data;

@Data
public class CreateTransactionDTO {
    private final String name;
    private final Integer transactionType;
}
