package com.dima.strategy;

import com.dima.model.Expense;

import java.nio.file.Path;
import java.util.List;

public interface ExportStrategy {
    void write(List<Expense> expenses, Path path);
    List<Expense> read(Path path);
}
