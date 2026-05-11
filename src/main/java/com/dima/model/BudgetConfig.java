package com.dima.model;

import java.math.BigDecimal;
import java.time.Month;

public record BudgetConfig(BigDecimal budgetAmount, Month month, int year) {
    public BudgetConfig {
        if (budgetAmount == null || budgetAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Budget amount must be non-negative");
        }
        if (month == null) {
            throw new IllegalArgumentException("Month cannot be null");
        }
        if (year < 0) {
            throw new IllegalArgumentException("Year must be non-negative");
        }
    }
}
