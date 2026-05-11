package com.dima.model;

import com.dima.exceptions.InvalidAmountException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class Expense implements Comparable<Expense> {
    private final String uniqueId;
    private final String description;
    private final BigDecimal amount;
    private final Category category;
    private final LocalDate date;
    private final Priority priority;

    public Expense(String description, BigDecimal amount,
                   Category category, LocalDate date, Priority priority) throws InvalidAmountException {
        this(UUID.randomUUID().toString().replace("-", ""),
                description, amount, category, date, priority);
    }

    // For copies/edits - preserve the ID
    private Expense(String id, String description, BigDecimal amount,
                    Category category, LocalDate date, Priority priority) throws InvalidAmountException {
        this.uniqueId = id;
        this.description = Objects.requireNonNull(description);

        amount = Objects.requireNonNull(amount, "amount must not be null");
        if (amount.signum() <= 0) {
            throw new InvalidAmountException("amount must be positive" + amount, amount);
        }
        this.amount = amount;

        this.category = Objects.requireNonNull(category);
        this.date = Objects.requireNonNull(date);
        this.priority = priority;

    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }

    public Expense withDescription(String newDescription) throws InvalidAmountException {
        return new Expense(this.uniqueId, newDescription, this.amount,
                this.category, this.date, this.priority);
    }

    public Expense withCategory(Category newCategory) throws InvalidAmountException {
        return new Expense(this.uniqueId, this.description, this.amount,
                newCategory, this.date, this.priority);
    }

    @Override
    public int compareTo(Expense other) {
        Objects.requireNonNull(other, "other must not be null");

        return Comparator
                .comparing(Expense::getDate)
                .thenComparing(Expense::getAmount)
                .thenComparing(Expense::getDescription)
                .thenComparing(e -> e.getCategory().name())
                .thenComparing(Expense::getPriority)
                .thenComparing(Expense::getUniqueId)
                .compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(uniqueId, expense.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uniqueId);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "uniqueId='" + uniqueId + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category=" + category +
                ", date=" + date +
                ", priority=" + priority +
                '}';
    }
}
