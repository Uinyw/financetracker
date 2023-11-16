# Use Case Descriptions Analysis of Data

## Generatre Report
```
Title: Generate Report

Primary Actors: User
Secondary Actors: -

Preconditions: -
Postconditions: A report data was generated

Flow:
1. The user requests an data report, if he wishes, he also cann specify for with category the report schould be generated.
2. The system collects all the data it gathered, if a specified category was choosen only the relevant data stays there.
3. The system returns the data present for the specified category.


Alternative flows:
2a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: UserID, optional Categories, stored data
```

## Generate Prediction
```
Title: Generate Prediction

Primary Actors: User
Secondary Actors: -

Preconditions: Pretrained Prediction Model for the given categories
Postconditions: A prediction is returned

Flow:
1. The user requests a prediction for a given timeframe, and optional categories.
2. The system collects all the data it gathered, it then uses the historical data to generate a cost prognosis for the timeframe and category.
3. The system returns the prognosis for the following timeframe.


Alternative flows:
2a. The provided data is invalid: The systems informs the user about his invalid input.

Information Requirements: UserID, timeframe, Categories, stored data, prediction mechanism
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

Information Requirements: UserID, timeframe, Categories, stored data, accountID, budgetGoal
```

## Analyse Patterns & Trends


## Improvement Analysis