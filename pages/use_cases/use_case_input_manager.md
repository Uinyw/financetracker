# Use Case Descriptions Analysis of Data

## Manual Input
```
Title: Manual Input

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A data entry is generated

Flow:
1. The user adds information about the price, the bank account and the categories. ALso the user adds whether this is a reoccuring expense or not.
2. The system creates an data entry about those categories and saves it.


Alternative flows:
1a. The user adds how much money he got giftet. He adds a bank account, catefories and that it's reoccuring.


Information Requirements: UserID, optional Categories,accountID, item-name, scheduled event
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

## Excel Input