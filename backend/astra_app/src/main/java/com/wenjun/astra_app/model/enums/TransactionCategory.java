package com.wenjun.astra_app.model.enums;

import lombok.Getter;

@Getter
public enum TransactionCategory {
    EXPENSE("Expense", 0),
    INCOME("Income", 1),
    TRANSFER("Transfer", 2);

    private final String name;
    private final Integer code;

    TransactionCategory(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}
