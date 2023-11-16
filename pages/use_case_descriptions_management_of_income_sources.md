# Use Case Descriptions Management of Income Sources

## Add Income Source

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

Information Requirements: ID, Name, Description, Category, Periodicity, Dynamic or Static, Monetary Amount
```

## Delete Income Source

```
Title: Delete Income Source

Primary Actors: User
Secondary Actors: -

Preconditions: The income source to delete exists in the set of income sources
Postconditions: The given income source is removed from the set of income sources

Flow:
1. The user enters the ID of the income source to delete.
2. The system queries for the corresponding income source and finds it.
3. The system removes the income source and all associated data from the set of income sources.


Alternative flows:
2a. No income source with the given ID exists: The systems informs the user about the non-existense of the income source to delete.

Information Requirements: ID of income source
```