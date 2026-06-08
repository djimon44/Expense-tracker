# Expense Tracker Plus

A feature-rich console-based expense tracking application built in Java. Designed as a learning project to practice core Java concepts including OOP, Streams, custom exceptions, design patterns, and file I/O.

## Features

- **Expense Management** — Add, update, and delete expenses with descriptions, amounts, categories, dates, and priority levels
- **Smart Sorting** — Sort expenses by amount, category, priority, or date using flexible Comparator-based ordering
- **Spending Analysis** — View monthly totals, category breakdowns, top N expenses, and filter by date range or category
- **Budget Tracking** — Set monthly budgets and get warnings when spending exceeds the limit
- **Data Persistence** — Export and import expenses in CSV or JSON format (Strategy pattern)

## Tech Stack

- Java 17
- Maven
- JUnit 5
- Gson (for JSON export)

## Project Structure

```
com.dima.expensetracker
├── model/          → Expense, Category, Priority, BudgetConfig
├── exception/      → Custom exception hierarchy (checked + unchecked)
├── repository/     → ExpenseRepository interface, InMemoryExpenseRepository
├── service/        → ExpenseService (business logic, Streams, budget checks)
├── strategy/       → ExportStrategy interface, CSV and JSON implementations
└── ui/             → Console menu system
```

## Core Concepts Practiced

- **OOP** — Immutable objects, records, enums, constructor chaining
- **Comparable & Comparator** — Natural ordering on Expense, static Comparator utility class
- **Custom Exceptions** — Checked (`InvalidAmountException`) and unchecked (`InvalidCategoryNameException`, `BudgetExceededException`) branches with exception chaining
- **Streams API** — Filtering, grouping, reducing, sorting, limiting
- **Design Patterns** — Repository, Strategy, Dependency Inversion
- **Collections** — HashMap for O(1) lookups, defensive copies, Optional for nullable results
- **JUnit 5** — Unit tests with Arrange-Act-Assert pattern

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+

### Build & Run

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.dima.Main"
```

### Run Tests

```bash
mvn test
```

## Status

🚧 **Work in progress**

- [x] Core model (Expense, Category, Priority)
- [x] Custom exception hierarchy
- [x] Repository layer with in-memory implementation
- [x] Service layer with Stream-based analysis
- [x] Budget tracking system
- [x] Unit tests for repository
- [ ] CSV export/import
- [ ] JSON export/import
- [ ] Console menu system
- [ ] Input validation and error handling in UI
- [ ] Service layer tests

## License

This project is for educational purposes.
