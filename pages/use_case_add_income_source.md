# Use Case Add Income Source

```
Title: Add Income Source

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: The given income source is added to set of income sources

Flow:
1. The user enters the required data for an income source.
2. The system validates the received data. A monetary amount can only be provided if the income source is static. If the income source is dynamic, income records can be added later.
3. The system creates a new income source and saves it.


Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: Name, Description, Category, Periodicity, Dynamic or Static, Monetary Amount
```