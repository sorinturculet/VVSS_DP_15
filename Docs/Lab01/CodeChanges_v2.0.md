# Code Changes – MyDrinkShop v2.0

**Team:** DP15
**Date:** 10.03.2026

All changes below were made based on findings from the Lab01 manual inspection (Requirements, Architecture, Coding checklists) and the SonarQube static analysis report (sonarqube_v1.md).

---

## 1. `pom.xml`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Missing `junit.version` property | – | `${junit.version}` was referenced but never defined, causing Maven build failure. Added `<junit.version>5.10.2</junit.version>` to `<properties>`. |

---

## 2. `repository/AbstractRepository.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Unnecessary cast | S1905 | Removed redundant `(List<E>)` cast on `StreamSupport.stream(...)`. |
| Commented-out code | S125 | Removed commented-out code blocks. |
| Duplicate method body | S4144 | `update()` was identical to `save()`. Added a key-existence check in `update()`: returns `null` if entity doesn't exist, otherwise puts it — making the two methods semantically distinct. |

---

## 3. `repository/file/FileAbstractRepository.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Public constructor on abstract class | S5993 | Changed constructor visibility from `public` to `protected`. |
| Commented-out code | S125 | Removed `//loadFromFile();` comment. |

---

## 4. `repository/file/FileOrderRepository.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Prefer `isEmpty()` | S7158 | Changed `sb.length() > 0` to `!sb.isEmpty()`. |

---

## 5. `domain/Order.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Duplicate method implementation | S4144 | `getTotal()` was a copy of `getTotalPrice()`. Changed `getTotal()` to delegate: `return getTotalPrice();`. |

---

## 6. `service/OrderService.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Commented-out code | S125 | Removed commented-out blocks. |

---

## 7. `service/ProductService.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Commented-out code | S125 | Removed all commented-out code blocks. |

---

## 8. `reports/DailyReportService.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Unused imports | S1128 | Removed unused `java.util.stream.Collectors` and `java.util.stream.StreamSupport`. |
| Commented-out code | S125 | Removed commented-out code. |

---

## 9. `export/CsvExporter.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Unused import | S1128 | Removed unused `java.util.Date` import. |
| Utility class instantiable | S1118 | Added `private CsvExporter() {}` constructor. |
| Unsafe `.get(0)` | – | Changed `.toList().get(0)` to `.findFirst().orElseThrow()` to avoid `IndexOutOfBoundsException`. |
| Lambda parameter parentheses | S1611 | Removed unnecessary parentheses around single lambda parameter `p1`. |
| Generic exception | S112 | Changed `throw new RuntimeException(e)` to `throw new UncheckedIOException(e)` — a specific standard exception for wrapping `IOException`. |

---

## 10. `receipt/ReceiptGenerator.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Utility class instantiable | S1118 | Added `private ReceiptGenerator() {}` constructor. |
| Unsafe `.get(0)` | – | Changed `.toList().get(0)` to `.findFirst().orElseThrow()`. |
| Lambda parameter parentheses | S1611 | Removed unnecessary parentheses around `p1`. |
| String concatenation in append | S3457 | Fixed `p.getNume()+": "` to use `.append(p.getNume()).append(": ")`. |

---

## 11. `service/validator/RetetaValidator.java`

| Finding | Checklist | Description |
|---------|-----------|-------------|
| Error message format | C08 | Added space before ingredient name and `\n` newline in the error accumulation message for negative/zero quantities. |
| Useless curly braces | S1602 | Removed unnecessary `{}` wrapping around the `forEach` lambda body. |

---

## 12. `service/StocService.java`

| Finding | Checklist | Description |
|---------|-----------|-------------|
| Unnecessary `(int)` cast | C06 | `setCantitate` accepts `double`; the `(int)` cast caused silent precision loss. Removed the cast: `s.setCantitate(s.getCantitate() - deScazut)`. |

---

## 13. `test/AppTest.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Unnecessary `public` modifier | S5786 | Removed `public` from class and test method — JUnit 5 does not require `public` on test classes or methods. |

---

## 14. `ui/DrinkShopController.java`

| Finding | SonarQube Rule | Description |
|---------|---------------|-------------|
| Multiple declarations per line | S1659 | Split `@FXML private TextField txtProdName, txtProdPrice;` into two separate declarations. Same for `txtNewIngredName, txtNewIngredCant`. |
| No input validation | C06 | Wrapped all `Double.parseDouble()` calls in `try/catch (NumberFormatException)` blocks; invalid input now shows an error dialog and aborts the operation. Affected methods: `onAddProduct()`, `onUpdateProduct()`, `onAddNewIngred()`. |
| Inefficient stream check | – | Changed `.toList().size() > 0` to `.anyMatch(...)` in `onAddProduct()` for cleaner and more efficient duplicate product check. |
