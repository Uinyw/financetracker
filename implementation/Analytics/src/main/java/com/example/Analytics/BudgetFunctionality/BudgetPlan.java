package com.example.Analytics.BudgetFunctionality;

import com.example.Analytics.AchievementStatus;
import com.example.Analytics.Category;
import lombok.Builder;
import org.openapitools.client.model.MonetaryAmount;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Builder
public class BudgetPlan {
private UUID id;
private Map<Category, MonetaryAmount> plan;
private Date startDate;
private AchievementStatus currentStatus;
}
