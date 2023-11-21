# Use Case Descriptions Analysis of Data

## Manually Input Data

```
 Title: Manual Input Data

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A data entry is generated

Flow:
1. The user adds information about the price for an item, assigns one bank account bank account and optionally adds categories. Also the user adds whether this is a recurring expense or not.
2. The system creates an data entry about those categories and saves it.


Alternative flows:
1a. The user adds how much money he got gifted. He adds a bank account, categories and if it's recurring.


Information Requirements: UserID, optional Categories, AccountID, item-name, scheduled event
```

## Camera Input

```
Title: Camera Input

Primary Actors: User
Secondary Actors: -

Preconditions: User has a camera
Postconditions: A data entry is generated

Flow:
1. The user takes a picture of a bill and uploads it.
2. The system creates an data entry using the information read from the bill and saves it.
3. The user adds categories to the entries.


Alternative flows:
2a. The photo can't be read, so the unfinished data entry is shown to the user to manually enter the missing data.


Information Requirements: UserID, optional Categories,accountID, item-name, scheduled event.
```

## API

```
Title: API Input

Primary Actors: User-System
Secondary Actors: -

Preconditions: Another System talking to the Rest-API
Postconditions: A data entry is generated

Flow:
1. The other system calls the appropriate function to create a new entry.
2. The system creates a new data entry based on the information provided by the new system.
3. The other system is able to check whether the new entry was created.


Alternative flows:
2a. The new entry can't be created, so an error message is returned.


Information Requirements: UserID, optional Categories, accountID, item-name, scheduled event.
```

## Excel Input

```
Title: Excel Input

Primary Actors: User
Secondary Actors: -

Preconditions: User has a camera
Postconditions: A data entry is generated

Flow:
1. The user uploads an Excel list, that suits a specific form.
2. The system creates data entries for the entries present in the Excel list and saves them.
3. The user adds missing data to the created entries.


Alternative flows:
2a. The Excel list has a wrong format and can't be read. A Error message is returned, stating this problem


Information Requirements: UserID, optional Categories,accountID, item-name, scheduled event.
```
