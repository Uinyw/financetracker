---
id: testing
---

# Testing [Lachenicht, Kässmann]

| Component | Coverage |
| :--- | :--- |
| BankAccount | ![coverage](../badges/jacoco-bankaccount.svg) |
| Transaction | ![coverage](../badges/jacoco-transaction.svg) |
| Product | ![coverage](../badges/jacoco-product.svg) |
| SavingsGoal | ![coverage](../badges/jacoco-savingsgoal.svg) |
| Analytics | ![coverage](../badges/jacoco-analytics.svg) |


## E2E Testing
For running E2E Test for mainline scenario, follow these steps:
1. Navigate to implementation/ui-financetracker/test
2. Run `npx wdio run wdio.conf.ts`