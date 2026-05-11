package com.dima.exceptions;

import java.math.BigDecimal;

public class InvalidAmountException extends ExpenseTrackerException {
    private final BigDecimal invalidAmount;

    public InvalidAmountException(String message, BigDecimal invalidAmount) {
        this(message, invalidAmount, null);
    }

    public InvalidAmountException(String message, BigDecimal invalidAmount, Throwable cause) {
        super(message, cause);
        this.invalidAmount = invalidAmount;
    }

    public BigDecimal getInvalidAmount() {
        return invalidAmount;
    }
}
