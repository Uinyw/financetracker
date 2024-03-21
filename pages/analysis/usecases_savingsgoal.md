---
id: usecases-savingsgoal
---

# Use Cases SavingsGoal [Kässmann]

## Create Savings Goal

```
Title: Create Savings Goal

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: The given savings goal is added to a set of savings goals

Flow:
1. The user enters the required data for a savings goal.
2. The system validates the received data. A monetary amount can only be provided if a type is fixed. A percentage rate can only be provided if the type is a percentage. A duration can only be provided if periodicity is not OneTime. A duration can only be provided if the category is Fixed-Term.
3. The system creates a new savings goal and saves it.


Alternative flows:
3a. The provided data is invalid: The system informs the user about his invalid input.

Information Requirements: ID, Name, Description, Category (Fixed-Term, Long-Term), Duration, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Current-Status (Not-Started, In-Progress, Not-Achieved, Achieved), Status-List, Type (Fixed, Percentage), Monetary Amount, Percentage Rate
```

## Update Savings Goal

```
Title: Update Savings Goal

Primary Actors: User
Secondary Actors: -

Preconditions: The savings goal to update exists in the set of savings goals
Postconditions: The savings goal data is updated

Flow:
1. The user enters the ID of the savings goal to edit.
2. The system queries for the corresponding savings goal and finds it.
3. The system displays the savings goal and the information currently associated with it.
4. The user edits the savings goal information.
2. The system validates the received data. A monetary amount can only be provided if a type is fixed. A percentage rate can only be provided if the type is a percentage. A duration can only be provided if periodicity is not OneTime. A duration can only be provided if the category is Fixed-Term.
7. The system updates the savings goal.

Alternative flows:
3a. No savings goal with the given ID exists: The system informs the user about the non-existence of the savings goal to edit.
7a. The provided data is invalid: The system informs the user about his invalid input.

Information Requirements: ID, Name, Description, Category (Fixed-Term, Long-Term), Duration, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Current-Status (Not-Started, In-Progress, Not-Achieved, Achieved), Past-Stat, Type (Fixed, Percentage), Monetary Amount, Percentage Rate
```

## Delete Savings Goal

```
Title: Delete Savings Goal

Primary Actors: User
Secondary Actors: -

Preconditions: The savings goal to delete exists in the set of savings goals
Postconditions: The given savings goal is removed from the set of savings goals

Flow:
1. The user enters the ID of the savings goal to delete.
2. The system queries for the corresponding savings goal and finds it.
3. The system removes the savings goal and all associated data from the set of savings goals.


Alternative flows:
3a. No savings goal with the given ID exists: The system informs the user about the non-existence of the savings goal to delete.

Information Requirements: ID of savings goal
```

## View Savings Goals

```
Title: View Savings Goals

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: All existing savings goals are displayed to the user

Flow:
1. The user requests to view an overview of his/her savings goals.
2. The system queries for all savings goals.
3. The system displays the current of the savings goals.


Alternative flows:
3a. No savings goals exist: The system informs the user about the fact, that no savings goals exist.

Information Requirements: -
```