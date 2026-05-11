package com.dima.model;

import java.util.Comparator;

public class ExpenseComparators {
    private ExpenseComparators() {}

    public static final Comparator<Expense> amountDescComparator = Comparator.comparing(Expense::getAmount).reversed();
    public static final Comparator<Expense> alphaComparator = Comparator.comparing(e -> e.getCategory().name());
    public static final Comparator<Expense> priorityComparator = Comparator.comparing(Expense::getPriority);
}
