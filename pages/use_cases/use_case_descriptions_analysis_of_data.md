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

## Analyse Patterns & Trends

## Improvement Analysis