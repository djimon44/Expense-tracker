package com.dima.exceptions;

public class InvalidCategoryNameException extends ExpenseTrackerRuntimeException {
    private final String invalidName;

    public InvalidCategoryNameException(String invalidName) {
        this("Category cannot be empty", invalidName, null);
    }

    public InvalidCategoryNameException(String message, String invalidName) {
        this(message, invalidName, null);
    }

    public InvalidCategoryNameException(String message, String invalidName, Throwable cause) {
        super(message, cause);
        this.invalidName = invalidName;
    }

    public String getInvalidName() {
        return invalidName;
    }
}
