package com.dima.service;

import com.dima.exceptions.BudgetExceededException;
import com.dima.model.BudgetConfig;
import com.dima.model.Category;
import com.dima.model.Expense;
import com.dima.model.ExpenseComparators;
import com.dima.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseService {
    private final ExpenseRepository repository;
    private final Map<YearMonth, BudgetConfig> budgetsByMonth;

    public ExpenseService(ExpenseRepository repository, Map<YearMonth, BudgetConfig> budgetsByMonth) {
        this.repository = repository;
        this.budgetsByMonth = budgetsByMonth;
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

    public void setBudget(YearMonth month, BudgetConfig budget) {
        budgetsByMonth.put(month, budget);
    }

    public void checkBudget(YearMonth month) {
        BudgetConfig budget = budgetsByMonth.get(month);
        if (budget == null) {
            return; // No budget set, so we consider it as not exceeded
        }
        List<Expense> monthlyExpense = expensesDuringPeriod(month.atDay(1), month.atEndOfMonth());
        BigDecimal totalExpenses = expensesSum(monthlyExpense);
        if (totalExpenses.compareTo(budget.budgetAmount()) > 0)
            throw new BudgetExceededException(budget.budgetAmount(), totalExpenses);
    }

    public boolean isWithinBudget(YearMonth month) {
        try {
            checkBudget(month);
            return true;
        } catch (BudgetExceededException e) {
            return false;
        }
    }
}
