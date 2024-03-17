---
id: uc-transaction
---

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
Title: Edit Transaction

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
6. The system validates the edited data. A monetary amount can only be provided if the transaction is static. If the transaction is dynamic, transaction records can be added. OneTime transaction have to be static.
7. The system updates the transaction.

Alternative flows:
3a. No transaction with the given ID exists: The systems informs the user about the non-existence of the transaction to edit.
7a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: BankAccountID, ID, Name, Description, Categories, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Dynamic or Static, Monetary Amount, transaction Records
```

## Categorize Transactions

```
Title: Categorize Transactions

Primary Actors: User
Secondary Actors: -

Preconditions: The transaction to categorize is currently added or edited
Postconditions: The given transaction is assigned to a category

Flow:
1. The user selects an existing category.
2. The system assigns the category to the transaction.


Alternative flows:
1a. The user creates a new category and selects it.
2a. The system saves the new category and assigns it to the transaction.

Information Requirements: ID, Name
```

## Add Notification

```
Title: Add Notification

Primary Actors: User
Secondary Actors: -

Preconditions: The transaction to add a notification for is currently added or edited
Postconditions: A new notification is created for the given transaction.

Flow:
1. The user creates a new notification for the transaction
2. The system saves the notification and assigns it to the transaction.

Information Requirements: ID, Name, Priority (High, Medium, Low), Periodicity
```

## Add Transaction Record

```
Title: Add Transaction Record

Primary Actors: User
Secondary Actors: -

Preconditions: The transaction to add transaction records for is currently edited
Postconditions: The transaction record is added to the transaction source

Flow:
1. The user adds transaction records.
2. The system validates the provided data. Transaction records can only be added if transaction source is dynamic and not OneTime.
3. The system creates the transaction record and assigns it to the transaction source.

Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Date, Monetary Amount, Custom References
```

## Delete Transaction

```
Title: Delete Transaction

Primary Actors: User
Secondary Actors: -

Preconditions: The transaction to delete exists in the set of transactions
Postconditions: The given transaction is removed from the set of transactions

Flow:
1. The user enters the ID of the transaction to delete.
2. The system queries for the corresponding transaction and finds it.
3. The system removes the transaction and all associated data from the set of transactions.


Alternative flows:
3a. No transaction with the given ID exists: The systems informs the user about the non-existence of the transaction to delete.

Information Requirements: ID of the transaction
```

## View Transaction Overview

```
Title: View Transaction Overview

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: All existing transaction are displayed to the user

Flow:
1. The user requests to view an overview of his transactions.
2. The system queries for all transactions.
3. The system displays all found transactions.


Alternative flows:
3a. No transaction exist: The systems informs the user about the fact, that no transactions exist.

Information Requirements: -
```
