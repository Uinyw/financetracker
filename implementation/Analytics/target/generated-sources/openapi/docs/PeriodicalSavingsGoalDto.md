

# PeriodicalSavingsGoalDto


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** |  |  [optional] |
|**name** | **String** |  |  [optional] |
|**description** | **String** |  |  [optional] |
|**achievementStatus** | **AchievementStatusDto** |  |  [optional] |
|**sourceBankAccountId** | **UUID** |  |  [optional] |
|**targetBankAccountId** | **UUID** |  |  [optional] |
|**goal** | [**MonetaryAmountDto**](MonetaryAmountDto.md) |  |  [optional] |
|**recurringRate** | **Double** |  |  [optional] |
|**recurringAmount** | [**MonetaryAmountDto**](MonetaryAmountDto.md) |  |  [optional] |
|**duration** | **String** |  |  [optional] |
|**periodicity** | **PeriodicityDto** |  |  [optional] |
|**savingsRecords** | [**List&lt;SavingsRecordDto&gt;**](SavingsRecordDto.md) |  |  [optional] |



