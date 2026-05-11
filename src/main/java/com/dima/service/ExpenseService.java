package com.dima.service;

import com.dima.model.Category;
import com.dima.model.Expense;
import com.dima.model.ExpenseComparators;
import com.dima.repository.ExpenseRepository;
import com.dima.repository.InMemoryExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getTotalExpenses() {
        List<Expense> expenses = repository.findAll();
        return expensesSum(expenses);
    }

    public Map<Category, BigDecimal> spendingBreakdown() {
        return repository.findAll().stream()
                .collect(
                        Collectors.groupingBy(Expense::getCategory,
                                Collectors.mapping(Expense::getAmount,
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))
                );
    }

    public List<Expense> topNExpenses(int n) {
        return repository.findAll(ExpenseComparators.amountDescComparator).stream()
                .limit(n)
                .toList();
    }

    public List<Expense> expensesByCategory(Category category) {
        return repository.findAll().stream()
                .filter(e -> e.getCategory().equals(category))
                .toList();
    }

    public List<Expense> expensesDuringPeriod(LocalDate startDate, LocalDate endDate) {
        return repository.findAll().stream()
                .filter(e -> !e.getDate().isBefore(startDate) && !e.getDate().isAfter(endDate))
                .toList();
    }

    private BigDecimal expensesSum(List<Expense> expenses) {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
