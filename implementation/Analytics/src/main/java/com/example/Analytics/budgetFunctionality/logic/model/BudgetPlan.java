package com.example.Analytics.budgetFunctionality.logic.model;

import com.example.Analytics.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import org.openapitools.client.model.MonetaryAmount;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Builder
@Entity
public class BudgetPlan {
    @Id
    private UUID id;
    private Map<Category, MonetaryAmount> plan;
    private Date startDate;
    private AchievementStatus currentStatus;
}
