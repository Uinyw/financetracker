# Use Case Descriptions Management of Transactions

## Add Transaction

```
Title: Add Transaction

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: The given transaction is added to set of transactions

Flow:
1. The user selects a bank account to create an transaction for.
2. The user enters the required data for a Transaction.
3. The system validates the received data. A monetary amount can only be provided if the transaction is static. If the transaction is dynamic, records can be added later. OneTime transaction have to be static.
4. The system creates a new transaction and saves it.


Alternative flows:
4a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: BankAccountID, ID, Name, Description, Categories, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Dynamic or Static, Monetary Amount
```

## Edit Transactions

```
Title: Edit Income Source

Primary Actors: User
Secondary Actors: -

Preconditions: The transaction to edit exists in the set of transactions
Postconditions: The transaction data is updated

Flow:
1. The user enters the ID of the transaction to edit.
2. The system queries for the corresponding transaction and finds it.
3. The system displays the transaction and the information currently associated with it.
4. The user edits the transaction information.
5. The user adds transaction records if transaction is dynamic. See use case "Add transaction Record".
6. The system validates the edited data. A monetary amount can only be provided if the transaction is static. If the transaction is dynamic, transaction records can be added. OneTime income sources have to be static.
7. The system updates the transaction.

Alternative flows:
3a. No transaction with the given ID exists: The systems informs the user about the non-existense of the transaction source to edit.
7a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: BankAccountID, ID, Name, Description, Categories, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Dynamic or Static, Monetary Amount, Income Records
```

## Categorize Transactions

```
Title: Categorize Transactions

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

## Add Notification

```
Title: Add Notification

Primary Actors: User
Secondary Actors: -

Preconditions: The income source to add a notification for is currently added or edited
Postconditions: A new notification is created for the given income source

Flow:
1. The user creates a new notification for the income source
2. The system saves the notication and assigns it to the icome source.

Information Requirements: ID, Name, Priority (High, Medium, Low), Periodicity
```

## Add Income Record

```
Title: Add Income Record

Primary Actors: User
Secondary Actors: -

Preconditions: The income source to add income records for is currently edited
Postconditions: The income record is added to the income source

Flow:
1. The user adds income records.
2. The system validates the provided data. Income records can only be added if income source is dynamic and not OneTime.
3. The system creates the income record and assigns it to the income source.

Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Date, Monetary Amount, Custom References
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
