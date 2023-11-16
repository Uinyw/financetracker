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
2. The system validates the received data. A monetary amount can only be provided if the income source is static. If the income source is dynamic, income records can be added later. OneTime income sources have to be static.
3. The system creates a new income source and saves it.


Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Description, Category, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Dynamic or Static, Monetary Amount
```

## Edit Income Source

```
Title: Edit Income Source

Primary Actors: User
Secondary Actors: -

Preconditions: The income source to edit exists in the set of income sources
Postconditions: The income source data is updated

Flow:
1. The user enters the ID of the income source to edit.
2. The system queries for the corresponding income source and finds it.
3. The system displays the income source and the information currently associated with it.
4. The user edits the income source information.
5. The user adds income records if income source is dynamic. See use case "Add Income Record".
6. The system validates the edited data. A monetary amount can only be provided if the income source is static. If the income source is dynamic, income records can be added. OneTime income sources have to be static.
7. The system updates the income source.

Alternative flows:
3a. No income source with the given ID exists: The systems informs the user about the non-existense of the income source to edit.
7a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Description, Category, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Dynamic or Static, Monetary Amount, Income Records
```

## Categorize Income Source

```
Title: Categorize Income Source

Primary Actors: User
Secondary Actors: -

Preconditions: The income source to categorize is currently added or edited
Postconditions: The given income source is assigned to a category

Flow:
1. The user selects an existing category.
2. The system assigns the category to the icome source.


Alternative flows:
1a. The user creates a new category and selects it.
2a. The system saves the new category and assigns it to the income source.

Information Requirements: ID, Name
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
3a. No income source with the given ID exists: The systems informs the user about the non-existense of the income source to delete.

Information Requirements: ID of income source
```

## View Income Source Overview

```
Title: View Income Source Overview

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: All existing income sources are displayed to the user

Flow:
1. The user requests to view an overview of his income sources.
2. The system queries for all income sources.
3. The system displays all found income sources.


Alternative flows:
3a. No income sources exist: The systems informs the user about the fact, that no income sources exist.

Information Requirements: -
```
