package com.dima.repository;

import com.dima.exceptions.InvalidAmountException;
import com.dima.model.Category;
import com.dima.model.Expense;
import com.dima.model.Priority;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryExpenseRepositoryTest {

    private static Expense coffee() throws InvalidAmountException {
        return new Expense("Coffee", new BigDecimal("4.50"),
                new Category("Food"), LocalDate.of(2026, 5, 1), Priority.DISCRETIONARY);
    }

    private static Expense rent() throws InvalidAmountException {
        return new Expense("Rent", new BigDecimal("1200.00"),
                new Category("Housing"), LocalDate.of(2026, 5, 3), Priority.ESSENTIAL);
    }

    private static Expense bus() throws InvalidAmountException {
        return new Expense("Bus", new BigDecimal("2.75"),
                new Category("Transport"), LocalDate.of(2026, 5, 2), Priority.ESSENTIAL);
    }

    @Test
    void shouldAddExpense() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense expense = coffee();

        repo.addExpense(expense);

        Optional<Expense> found = repo.readExpense(expense.getUniqueId());
        assertTrue(found.isPresent());
        assertEquals(expense, found.get());
    }

    @Test
    void addExpense_duplicateId_keepsOriginal() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense original = coffee();
        Expense sameIdDifferentDescription = original.withDescription("Latte");

        repo.addExpense(original);
        repo.addExpense(sameIdDifferentDescription);

        Optional<Expense> found = repo.readExpense(original.getUniqueId());
        assertTrue(found.isPresent());
        assertSame(original, found.get(), "putIfAbsent must not overwrite an existing entry");
        assertEquals("Coffee", found.get().getDescription());
    }

    @Test
    void updateExpense_existingId_replacesAndReturnsTrue() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense original = coffee();
        repo.addExpense(original);

        Expense updated = original.withDescription("Espresso");
        boolean result = repo.updateExpense(updated);

        assertTrue(result);
        Optional<Expense> found = repo.readExpense(original.getUniqueId());
        assertTrue(found.isPresent());
        assertEquals("Espresso", found.get().getDescription());
    }

    @Test
    void updateExpense_missingId_returnsFalseAndDoesNotInsert() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense neverAdded = coffee();

        boolean result = repo.updateExpense(neverAdded);

        assertFalse(result, "computeIfPresent must not create a new entry");
        assertTrue(repo.readExpense(neverAdded.getUniqueId()).isEmpty());
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void readExpense_existingId_returnsExpense() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense expense = coffee();
        repo.addExpense(expense);

        Optional<Expense> found = repo.readExpense(expense.getUniqueId());

        assertTrue(found.isPresent());
        assertEquals(expense, found.get());
    }

    @Test
    void readExpense_nonexistentId_returnsEmpty() {
        ExpenseRepository repo = new InMemoryExpenseRepository();

        Optional<Expense> found = repo.readExpense("does-not-exist");

        assertTrue(found.isEmpty());
    }

    @Test
    void findAll_emptyRepo_returnsEmptyList() {
        ExpenseRepository repo = new InMemoryExpenseRepository();

        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void findAll_multipleExpenses_returnsAll() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense c = coffee();
        Expense r = rent();
        Expense b = bus();
        repo.addExpense(c);
        repo.addExpense(r);
        repo.addExpense(b);

        List<Expense> all = repo.findAll();

        assertEquals(3, all.size());
        assertTrue(all.contains(c));
        assertTrue(all.contains(r));
        assertTrue(all.contains(b));
    }

    @Test
    void findAll_withComparator_returnsSorted() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense c = coffee(); // 2026-05-01
        Expense r = rent();   // 2026-05-03
        Expense b = bus();    // 2026-05-02
        repo.addExpense(r);
        repo.addExpense(c);
        repo.addExpense(b);

        List<Expense> byDate = repo.findAll(Comparator.comparing(Expense::getDate));

        assertEquals(List.of(c, b, r), byDate);
    }

    @Test
    void findAll_withReverseComparator_returnsSortedDescending() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense c = coffee();
        Expense r = rent();
        Expense b = bus();
        repo.addExpense(c);
        repo.addExpense(b);
        repo.addExpense(r);

        List<Expense> byAmountDesc = repo.findAll(
                Comparator.comparing(Expense::getAmount).reversed());

        assertEquals(List.of(r, c, b), byAmountDesc);
    }

    @Test
    void deleteExpense_existingId_removesAndReturnsTrue() throws InvalidAmountException {
        ExpenseRepository repo = new InMemoryExpenseRepository();
        Expense expense = coffee();
        repo.addExpense(expense);

        boolean removed = repo.deleteExpense(expense.getUniqueId());

        assertTrue(removed);
        assertTrue(repo.readExpense(expense.getUniqueId()).isEmpty());
    }

    @Test
    void deleteExpense_nonexistentId_returnsFalse() {
        ExpenseRepository repo = new InMemoryExpenseRepository();

        boolean removed = repo.deleteExpense("does-not-exist");

        assertFalse(removed);
    }
}