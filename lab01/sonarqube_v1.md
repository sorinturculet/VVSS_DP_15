# SonarQube Analysis Report - MyDrinkShop DP15
**Tool:** SonarQube for IDE (SonarLint)
**Date:** 10.03.2026
**Total:** 25 issues in 14 files

---

## AbstractRepository.java (4 issues)
| Line | Issue | Rule |
|------|-------|------|
| 37, 13 | Update this method so that its implementation is not identical to "save" on line 26 | java:S4144 |
| 8, 41 | Rename this generic name to match the regular expression '^[A-Z][0-9]?$' | java:S119 |
| 20, 15 | Remove this unnecessary cast to "List" | java:S1905 |
| 21, 22 | This block of commented-out lines of code should be removed | java:S125 |

## App.java (1 issue)
| Line | Issue | Rule |
|------|-------|------|
| 8, 8 | Replace this use of System.out by a logger | java:S106 |

## AppTest.java (2 issues)
| Line | Issue | Rule |
|------|-------|------|
| - | Remove this 'public' modifier | java:S5786 |
| - | Remove this 'public' modifier | java:S5786 |

## CsvExporter.java (3 issues)
| Line | Issue | Rule |
|------|-------|------|
| - | Replace generic exceptions with specific library exceptions or a custom exception | java:S112 |
| - | Add a private constructor to hide the implicit public one | java:S1118 |
| - | Remove the parentheses around the "p1" parameter | java:S1611 |

## DailyReportService.java (1 issue)
| Line | Issue | Rule |
|------|-------|------|
| - | This block of commented-out lines of code should be removed | java:S125 |

## DrinkShopController.java (2 issues)
| Line | Issue | Rule |
|------|-------|------|
| - | Declare "txtProdPrice" on a separate line | java:S1659 |
| - | Declare "txtNewIngredCant" on a separate line | java:S1659 |

## FileAbstractRepository.java (3 issues)
| Line | Issue | Rule |
|------|-------|------|
| - | Change the visibility of this constructor to "protected" | java:S5993 |
| - | Rename this generic name to match the regular expression '^[A-Z][0-9]?$' | java:S119 |
| - | This block of commented-out lines of code should be removed | java:S125 |

## FileOrderRepository.java (1 issue)
| Line | Issue | Rule |
|------|-------|------|
| - | Use "isEmpty()" to check whether a "StringBuilder" is empty or not | java:S7158 |

## Order.java (1 issue)
| Line | Issue | Rule |
|------|-------|------|
| - | Update this method so that its implementation is not identical to "getTotalPrice" on line 33 | java:S4144 |

## OrderService.java (1 issue)
| Line | Issue | Rule |
|------|-------|------|
| 35, 18 | This block of commented-out lines of code should be removed | java:S125 |

## ProductService.java (2 issues)
| Line | Issue | Rule |
|------|-------|------|
| - | This block of commented-out lines of code should be removed [+3 locations] | java:S125 |
| - | This block of commented-out lines of code should be removed | java:S125 |

## ReceiptGenerator.java (2 issues)
| Line | Issue | Rule |
|------|-------|------|
| - | Add a private constructor to hide the implicit public one | java:S1118 |
| - | Remove the parentheses around the "p1" parameter | java:S1611 |

## Repository.java (1 issue)
| Line | Issue | Rule |
|------|-------|------|
| 5, 28 | Rename this generic name to match the regular expression '^[A-Z][0-9]?$' | java:S119 |

## RetetaValidator.java (1 issue)
| Line | Issue | Rule |
|------|-------|------|
| - | Remove useless curly braces around statement | java:S1602 |
