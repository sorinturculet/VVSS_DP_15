# Requirements – MyDrinkShop v2.0

**Team:** DP15
**Date:** 10.03.2026
**Version:** 2.0 (revised after Lab01 inspection)

---

## 1. Overview

MyDrinkShop is a desktop JavaFX application for managing a drink shop. It allows operators to manage the product catalogue, ingredient recipes (retete), ingredient stock, and customer orders, as well as generate daily revenue reports and export order data to CSV.

---

## 2. Functional Requirements

### 2.1 Product Management

- **FR-01** The system shall allow adding a new product with a unique ID (equal to the ID of an associated recipe), name, price, category (CategorieBautura), and type (TipBautura).
  - *v2.0 clarification:* A product can only be added if a recipe exists with the same ID. If a product with that recipe's ID already exists, the system shall display a warning and abort the operation.
- **FR-02** The system shall allow updating an existing product's name, price, category, and type.
  - *v2.0 clarification:* Price input must be validated as a numeric decimal value; invalid input shall display an error message and abort the operation without crashing.
- **FR-03** The system shall allow deleting an existing product by ID.
- **FR-04** The system shall display all products in a table with columns: ID, Name, Price, Category, Type.
- **FR-05** The system shall allow filtering products by category (CategorieBautura). Selecting `ALL` returns all products.
- **FR-06** The system shall allow filtering products by type (TipBautura). Selecting `ALL` returns all products.

### 2.2 Recipe (Reteta) Management

- **FR-07** The system shall allow creating a new recipe composed of one or more named ingredients, each with a strictly positive quantity.
  - *v2.0 clarification:* A recipe with an empty ingredient list or with any ingredient having quantity ≤ 0 shall be rejected by the validator with a descriptive error message.
- **FR-08** The system shall allow adding individual ingredients to a new recipe before saving it.
  - *v2.0 clarification:* The ingredient quantity field must be validated as a numeric decimal; invalid input shall show an error message without crashing.
- **FR-09** The system shall allow removing an ingredient from the new recipe before saving.
- **FR-10** The system shall allow clearing all ingredients from the pending new recipe.
- **FR-11** The system shall display existing recipes in a table with columns: ID, Ingredient Summary.

### 2.3 Stock (Stoc) Management

- **FR-12** The system shall maintain a stock of ingredients with quantities.
- **FR-13** Before processing an order item that requires a recipe, the system shall verify that sufficient stock exists for all required ingredients.
- **FR-14** When an order is finalized, the system shall deduct the consumed ingredient quantities from stock. Deductions shall preserve decimal precision (no integer truncation).

### 2.4 Order Management

- **FR-15** The system shall allow adding products to the current order with a selected quantity (1–10).
  - *v2.0 clarification:* Both a product and a quantity must be selected; missing either shall display an error message.
- **FR-16** The system shall allow removing an item from the current order.
- **FR-17** The system shall display the running total price of the current order.
- **FR-18** The system shall allow finalizing the current order, which: saves the order, generates a printed receipt, and resets the current order for the next customer.
- **FR-19** The system shall display a formatted receipt (bon fiscal) after order finalization, showing each product's name, unit price, quantity, line total, and the overall order total.

### 2.5 Reporting and Export

- **FR-20** The system shall compute and display the total daily revenue (sum of totals of all saved orders).
- **FR-21** The system shall allow exporting all orders to a CSV file with columns: OrderId, Product, Quantity, Price; including per-order totals and an overall daily total with the current date.

---

## 3. Non-Functional Requirements

- **NFR-01** The application shall be implemented in Java 21 with JavaFX.
- **NFR-02** Data shall be persisted using file-based repositories (text files).
- **NFR-03** All user-facing validation errors shall be displayed via JavaFX Alert dialogs; no unhandled exceptions shall propagate to the user as raw stack traces.
  - *v2.0 clarification added:* Input parsing (e.g., `Double.parseDouble`) must be wrapped in try/catch blocks.
- **NFR-04** The codebase shall follow the Validator pattern; all domain validators shall throw `ValidationException` with a descriptive, properly formatted message.
- **NFR-05** Utility classes with only static methods (CsvExporter, ReceiptGenerator) shall not be instantiable (private constructor).
- **NFR-06** The application shall use the Repository pattern for data access, with a generic `Repository<ID, E>` interface.

---

## 4. Constraints

- **C-01** Product IDs are shared with Recipe IDs (1-to-1 relationship between product and recipe).
- **C-02** Order item quantities are in the range [1, 10].
- **C-03** Ingredient quantities and product prices must be strictly positive decimal numbers.

---

## 5. Changes from v1.0

| # | Change | Reason |
|---|--------|--------|
| FR-01 | Clarified: product creation requires existing recipe; duplicate check added | Code inspection finding (C06 – no guard) |
| FR-02, FR-08 | Clarified: numeric input must be validated with error dialog | Inspection finding C06 – NumberFormatException not handled |
| FR-07 | Clarified: validator must reject empty ingredient list and non-positive quantities | Inspection finding C08 – validator error message formatting |
| FR-14 | Clarified: stock deduction preserves decimal precision | Code bug: unnecessary `(int)` cast in StocService |
| NFR-03 | Added explicit requirement for try/catch on input parsing | SonarQube + inspection finding |
| NFR-05 | Added requirement for private constructors on utility classes | SonarQube S1118 |
