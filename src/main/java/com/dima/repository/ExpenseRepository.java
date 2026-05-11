package com.dima.repository;

import com.dima.model.Expense;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {
    void addExpense(Expense expense);
    Optional<Expense> readExpense(String id);
    List<Expense> findAll();
    List<Expense> findAll(Comparator<Expense> comparator);
    boolean deleteExpense(String id);
    boolean updateExpense(Expense expense);
}
