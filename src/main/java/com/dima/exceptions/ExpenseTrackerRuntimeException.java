package com.dima.exceptions;

public class ExpenseTrackerRuntimeException extends RuntimeException {
    public ExpenseTrackerRuntimeException(String message) {
        super(message);
    }

    public ExpenseTrackerRuntimeException(String message, Throwable cause) {
        super (message, cause);
    }
}
