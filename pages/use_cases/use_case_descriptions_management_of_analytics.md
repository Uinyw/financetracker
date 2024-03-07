# Use Case Descriptions Analytics

## Generate Data Report

```
Title: Generate Data Report

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A data report is generated and saved on the users device

Flow:
1. The user selects filters for the data report.
2. The user requests a data report with the specified filters.
3. The system collects the required data.
4. The system generates the data report in the format of an Excel file.
5. The user saves the data report on their device.


Alternative flows:
2a. The provided filter data is invalid: The systems informs the user about his invalid input.

Information Requirements: 
- Filters: Bank account, Timeframe, Categories
- General: UserID, Stored data
```

## Generate Prediction

```
Title: Generate Prediction

Primary Actors: User
Secondary Actors: -

Preconditions: Pretrained prediction model for the given categories
Postconditions: A prediction is returned

Flow:
1. The user requests a prediction for a given timeframe, and optional categories.
2. The system collects all the data it gathered, it then uses the historical data to generate a cost prognosis for the timeframe and category.
3. The system returns the prognosis for the following timeframe.


Alternative flows:
2a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: UserID, timeframe, Categories, stored data, prediction mechanism, Management of Expenses
```

## Generate Budgeting

```
Title: Generate Budgeting

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A budget plan is returned

Flow:
1. The user creates a budget goal, that he/she want's to reach.
2. The user adds the specific categories of the budget goal and the bank account.
2. The system returns to the user, how much he/she is able to spend to be within the budget goal.


Alternative flows:
2a. The provided data is invalid: The systems informs the user about his/her invalid input.

Information Requirements: UserID, timeframe, Categories, stored data, AccountID, BudgetGoal
```
