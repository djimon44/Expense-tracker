package com.dima.repository;

import com.dima.model.Expense;

import java.util.*;

public class InMemoryExpenseRepository implements ExpenseRepository {
    private final Map<String, Expense> internalRepository = new HashMap<>();

    @Override
    public void addExpense(Expense expense) {
        internalRepository.putIfAbsent(expense.getUniqueId(), expense);
    }

    @Override
    public Optional<Expense> readExpense(String id) {
        return Optional.ofNullable(internalRepository.get(id));
    }

    @Override
    public List<Expense> findAll() {
        return internalRepository.values().stream()
                .toList();
    }

    @Override
    public List<Expense> findAll(Comparator<Expense> comparator) {
        return internalRepository.values().stream()
                .sorted(comparator)
                .toList();
    }

    @Override
    public boolean deleteExpense(String id) {
        return internalRepository.remove(id) != null;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        return internalRepository.computeIfPresent(expense.getUniqueId(), (k, v) -> expense) != null;
    }
}
