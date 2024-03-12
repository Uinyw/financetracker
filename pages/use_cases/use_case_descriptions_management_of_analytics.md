# Use Case Descriptions Analytics

## Generate Report

```
Title: Generate Report

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A data report is generated and saved on the user's device

Flow:
1. The user selects filters for the data report.
2. The user requests a data report with the specified filters.
3. The system collects the required data.
4. The system generates the data report in the format of an Excel file.
5. The user saves the data report on their device.


Alternative flows:
2a. The provided filter data is invalid: The system informs the user about his invalid input.

Information Requirements: 
- Filters: Bank account, Timeframe, Categories
- General: UserID, Stored data
```

## Generate Forecast

Title: Generate Forecast

Primary Actors: User
Secondary Actors: -

Preconditions: Existing data in the system
Postconditions: A prediction is returned

Flow:

1. The user requests a prediction for a given time.
2. The system collects all the data it gathered, it then uses the historical data to generate a cost prognosis for the timeframe.
3. The system returns the prognosis for the following timeframe.

Alternative flows:
2a. The provided data is invalid: The system informs the user about his invalid input.

Information Requirements: UserID, timeframe, stored data, management of expenses

## Generate Budget Plan

```
Title: Generate Budget Plan

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A budget plan is returned

Flow:
1. The user creates a budget goal, that he/she wants to reach.
2. The user adds the specific categories of the budget goal and the bank account.
2. The system returns to the user, how much he/she is able to spend to be within the budget goal.


Alternative flows:
2a. The provided data is invalid: The system informs the user about his/her invalid input.

Information Requirements: UserID, timeframe, Categories, stored data, AccountID
```

## Generate Diet Plan

```
Title: Generate Diet Plan

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A diet plan is returned

Flow:
1. The user requests a diet plan.
2. The user adds a specific time period to that diet plan.
2. The system returns the nutrition value of the products consumed in that timeframe.


Alternative flows:
2a. The provided data is invalid: The system informs the user about his/her invalid input.

Information Requirements: UserID, timeframe, nutrition, stored data
```
