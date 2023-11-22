# Use Case Descriptions Management of Expenses

## Add Expense

```
Title: Add Expense

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: The given expense is added to set of expenses

Flow:
1. The user enters the required data for an expense.
2. The system validates the received data. A monetary amount can only be provided if the expense is static. If the expense is dynamic, expense records can be added later. OneTime expenses have to be static.
3. The system creates a new expense and saves it.


Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Description, Categories, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Dynamic or Static, Monetary Amount
```

## Edit Expense

```
Title: Edit Expense

Primary Actors: User
Secondary Actors: -

Preconditions: The expense to edit exists in the set of expenses
Postconditions: The expense data is updated

Flow:
1. The user enters the ID of the expense to edit.
2. The system queries for the corresponding expense and finds it.
3. The system displays the expense and the information currently associated with it.
4. The user edits the expense information.
5. The user adds expense records if expense is dynamic. See use case "Add Expense Record".
6. The system validates the edited data. A monetary amount can only be provided if the expense is static. If the expense is dynamic, expense records can be added. OneTime expenses have to be static.
7. The system updates the expense.

Alternative flows:
3a. No expense with the given ID exists: The systems informs the user about the non-existense of the expense to edit.
7a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Description, Categories, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Dynamic or Static, Monetary Amount, Income Records
```

## Categorize Expense

```
Title: Categorize Expense

Primary Actors: User
Secondary Actors: -

Preconditions: The expense to categorize is currently added or edited
Postconditions: The given expense is assigned to a category

Flow:
1. The user selects an existing category.
2. The system assigns the category to the icome source.


Alternative flows:
1a. The user creates a new category and selects it.
2a. The system saves the new category and assigns it to the expense.

Information Requirements: ID, Name
```

## Add Notification

```
Title: Add Notification

Primary Actors: User
Secondary Actors: -

Preconditions: The expense to add a notification for is currently added or edited
Postconditions: A new notification is created for the given expense

Flow:
1. The user creates a new notification for the expense
2. The system saves the notication and assigns it to the icome source.

Information Requirements: ID, Name, Priority (High, Medium, Low), Periodicity
```

## Add Expense Record

```
Title: Add Expense Record

Primary Actors: User
Secondary Actors: -

Preconditions: The expense to add expense records for is currently edited
Postconditions: The income record is added to the expense

Flow:
1. The user adds expense records.
2. The system validates the provided data. Expense records can only be added if expense is dynamic and not OneTime.
3. The system creates the expense record and assigns it to the expense.

Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Date, Monetary Amount, Custom References
```

## Delete Expense

```
Title: Delete Expense

Primary Actors: User
Secondary Actors: -

Preconditions: The expense to delete exists in the set of expenses
Postconditions: The given expense is removed from the set of expenses

Flow:
1. The user enters the ID of the expense to delete.
2. The system queries for the corresponding expense and finds it.
3. The system removes the expense and all associated data from the set of expenses.


Alternative flows:
3a. No expense with the given ID exists: The systems informs the user about the non-existense of the expense to delete.

Information Requirements: ID of expense
```

## View Expense Overview

```
Title: View Expense Overview

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: All existing expenses are displayed to the user

Flow:
1. The user requests to view an overview of his expenses.
2. The system queries for all expenses.
3. The system displays all found expenses.


Alternative flows:
3a. No expenses exist: The systems informs the user about the fact, that no expenses exist.

Information Requirements: -
```
