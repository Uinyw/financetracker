# Use Case Descriptions Management of Shopping

## Create Product Entry

```
Title: Create Product Entry

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: The given product is added to set of products

Flow:
1. The user enters the required data for a new product entry.
2. The system validates the received data.
3. The system creates a new product entry and saves it.


Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Category, Monetary Amount
```

## Delete Product Entry

```
Title: Delete Product Entry

Primary Actors: User
Secondary Actors: -

Preconditions: The product entry to delete exists in the set of product entries
Postconditions: The given product entry is removed from the set of product entries

Flow:
1. The user enters the ID or a name of the product entry to delete it.
2. The system queries for the corresponding product entry and finds it.
3. The system removes the product entry and all associated data from the set to it.


Alternative flows:
2a. No product entry with the given ID or name exists: The systems informs the user about the non-existense of the product entry to delete.

Information Requirements: ID or name of the product entry
```

## Edit Product Entry

```
Title: Edit Product entry

Primary Actors: User
Secondary Actors: -

Preconditions: The product entry to edit exists in the set of product entries
Postconditions: The given product entry is edited

Flow:
1. The user enters the ID or a name of the product entry to edit it.
2. The system queries for the corresponding product entry and finds it.
3. The system shows the user the corresponding entry and all data associated to it.
4. The user edits the information and submits it.
5. The system edits the entry.


Alternative flows:
2a. No product entry with the given ID exists: The systems informs the user about the non-existense of the product entry to edit.

Information Requirements: ID of the product entry, associated product entry
```
