---
id: testing
---

# Testing [Lachenicht, Kässmann]

The microservices are tested using unit, integration and end-to-end (E2E) tests.
Unit tests validate individual components of the microservices in isolation.
Integration tests validate the interaction between the components of a microservice.
E2E tests validate the interaction of all microservices.

## Unit & Integration Testing

How to run unit & integration tests for each microservice:

1. Navigate to folder `financetracker`
2. Run the following commands

```
mvn clean test --file implementation/bankaccount/pom.xml
mvn clean test --file implementation/transaction/pom.xml
mvn clean test --file implementation/product/pom.xml
mvn clean test --file implementation/savingsgoal/pom.xml
mvn clean test --file implementation/analytics/pom.xml
```

The following code coverage is achieved by unit & integration tests (only the target folder and the application class with the main method are excluded):

| Microservice | Coverage |
| :--- | :--- |
| BankAccount | ![coverage](../badges/jacoco-bankaccount.svg) |
| Transaction | ![coverage](../badges/jacoco-transaction.svg) |
| Product | ![coverage](../badges/jacoco-product.svg) |
| SavingsGoal | ![coverage](../badges/jacoco-savingsgoal.svg) |
| Analytics | ![coverage](../badges/jacoco-analytics.svg) |


## E2E Testing

How to run [E2E Tests](../implementation/ui-financetracker/test/specs/mainline-scenario.e2e.ts):

1. Navigate to folder `financetracker/implementation/ui-financetracker/test`
2. Run the following command

```
npx wdio run wdio.conf.ts
```
