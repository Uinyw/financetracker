package com.example.Analytics.logic.model.budgetModel;

import jakarta.persistence.Id;
import lombok.*;
import org.openapitools.client.model.AchievementStatusDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetPlan {
    private UUID id;
    private LocalDate startDate;
    private List<BudgetElement> budgetElementList;
    private AchievementStatus currentStatus;

    public BudgetPlan(List<BudgetElement> budgetElementList){
        this.budgetElementList = budgetElementList;
        this.currentStatus = AchievementStatus.ACHIEVED;
        this.startDate = LocalDate.now();
        this.id = UUID.randomUUID();
    }
    public BudgetPlan(List<BudgetElement> budgetElementList, AchievementStatus achievementStatus){
        this.budgetElementList = budgetElementList;
        this.currentStatus = achievementStatus;
        this.startDate = LocalDate.now();
        this.id = UUID.randomUUID();
    }
}
