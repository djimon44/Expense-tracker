package com.dima.model;

import com.dima.exceptions.InvalidCategoryNameException;

import java.util.Objects;

public record Category(String name) {
    public Category {
        name = Objects.requireNonNull(name).trim();
        if (name.isEmpty()) {
            throw new InvalidCategoryNameException("Category name cannot be empty");
        }
    }
}
