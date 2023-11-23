# existenseUse Case Descriptions Management of Saving Goals

## Add Savings Goal

```
Title: Add Savings Goal

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: The given savings goal is added to set of savings goals

Flow:
1. The user enters the required data for an savings goal.
2. The system validates the received data. A monetary amount can only be provided if type is fixed. A percentage rate can only be provided if type is percentage. A duration can only be provided if periodicity is not OneTime. A duration can only be provided if category is Fixed-Term.
3. The system creates a new savings goal and saves it.


Alternative flows:
3a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: ID, Name, Description, Category (Fixed-Term, Long-Term), Duration, Periodicity (OneTime, Monthly, Quarterly, HalfYearly, Yearly), Current-Status (Not-Started, In-Progress, Not-Achieved, Achieved), Status-List, Type (Fixed, Percentage), Monetary Amount, Percentage Rate
```

## Edit Savings Goal

```
Title: Edit Savings Goal

Primary Actors: User
Secondary Actors: -

Preconditions: The savings goal to edit exists in the set of savings goals
Postconditions: The savings goal data is updated

Flow:
1. The user enters the ID of the savings goal to edit.
2. The system queries for the corresponding savings goal and finds it.
3. The system displays the savings goal and the information currently associated with it.
4. The user edits the savings goal information.
2. The system validates the received data. A monetary amount can only be provided if type is fixed. A percentage rate can only be provided if type is percentage. A duration can only be provided if periodicity is not OneTime. A duration can only be provided if category is Fixed-Term.
7. The system updates the savings goal.

Alternative flows:
3a. No savings goal with the given ID exists: The systems informs the user about the non-existence of the savings goal to edit.
7a. The provided data is invalid: The systems informs the user about his invalid input.

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
3a. No savings goal with the given ID exists: The systems informs the user about the non-existence of the savings goal to delete.

Information Requirements: ID of savings goal
```

## View Savings Goal Overview

```
Title: View Savings Goal Overview

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: All existing savings goals are displayed to the user

Flow:
1. The user requests to view an overview of his savings goals.
2. The system queries for all savings goals.
3. The system displays all found savings goals.


Alternative flows:
3a. No savings goals exist: The systems informs the user about the fact, that no savings goals exist.

Information Requirements: -
```

## View Savings Goal Status

```
Title: View Savings Goal Status

Primary Actors: User
Secondary Actors: -

Preconditions: The savings goal to view the status for exists in the set of savings goals
Postconditions: The saving goal status for the savings goal is displayed to the user

Flow:
1. The user enters the ID of the savings goal to view the status for.
2. The system queries for the corresponding savings goal and finds it.
3. The system displays the overall, current and all past statuses of the savings goal. The current status is the status for the current period (according to the periodicity). The past statuses are the statuses for past periods.

Alternative flows:
3a. No savings goal with the given ID exists: The systems informs the user about the non-existence of the savings goal to display the status for.

Information Requirements: ID of savings goal
```
