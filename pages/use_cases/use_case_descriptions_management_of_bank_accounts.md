# Use Case Descriptions Management of Bank Accounts

## Add Bank Account

```
Title: Add Bank Account

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: The given bank account is created and added to set of bank accounts

Flow:
1. The user enters the required data for an bank account such as the name.
2. The system validates the received data and creates a new empty bank account.


Alternative flows:
2a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: user-ID, account-name
```

## Delete Bank Account

```
Title: Delete Bank Account

Primary Actors: User
Secondary Actors: -

Preconditions: The bank account to delete exists in the set of bank accounts for an specific user
Postconditions: The given bank account is removed from the set of bank accounts

Flow:
1. The user enters the ID or the name of the bank account to delete it.
2. The system queries for the corresponding bank account and finds it.
3. The system removes the bank account and all associated data from the set of bank accounts.


Alternative flows:
2a. No bank account with the given ID or name exists: The systems informs the user about the non-existence of the bank account to delete.

Information Requirements: ID or name of the bank account, bank account user ID
```
