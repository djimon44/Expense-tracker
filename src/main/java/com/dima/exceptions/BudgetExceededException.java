package com.dima.exceptions;

import java.math.BigDecimal;

public class BudgetExceededException extends ExpenseTrackerRuntimeException {
    private final BigDecimal budgetThreshold;
    private final BigDecimal amount;
    private final BigDecimal exceeded;

    public BudgetExceededException(BigDecimal budgetThreshold, BigDecimal amount) {
        this("Monthly budget of " + budgetThreshold + " exceeded by " + amount.subtract(budgetThreshold) + " with amount " + amount, budgetThreshold, amount, null);
    }

    public BudgetExceededException(String message, BigDecimal budgetThreshold, BigDecimal amount) {
        this(message, budgetThreshold, amount, null);
    }

    public BudgetExceededException(String message, BigDecimal budgetThreshold, BigDecimal amount, Throwable cause) {
        super(message, cause);
        this.budgetThreshold = budgetThreshold;
        this.amount = amount;
        this.exceeded = amount.subtract(budgetThreshold);
    }

    public BigDecimal getBudgetThreshold() { return budgetThreshold; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getExceeded() { return exceeded; }
}