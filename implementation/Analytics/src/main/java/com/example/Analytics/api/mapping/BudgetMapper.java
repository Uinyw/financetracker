package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.budgetModel.AchievementStatus;
import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.budgetModel.BudgetPlan;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import org.openapitools.model.*;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {

    public FilterElement filterDtoToElement(ReportFilterDto reportFilterDto) {
        //TODO
        return null;
    }

    public BudgetPlanDto budgetPlanToDto(BudgetPlan budgetPlan){
        return BudgetPlanDto.builder()
                .id(budgetPlan.getId())
                .achievementStatus(achievementStatusToDto(budgetPlan.getCurrentStatus()))
                .date(budgetPlan.getStartDate().toString())
                .plan(budgetPlan.getBudgetElementList().stream().map(this::budgetElementToDto).toList())
                .build();
    }
    private BudgetAchievementStatusDto achievementStatusToDto(AchievementStatus achievementStatus){
        return switch (achievementStatus){
            case FAILED -> BudgetAchievementStatusDto.FAILED;
            case ACHIEVED -> BudgetAchievementStatusDto.ACHIEVED;
        };
    }
    private BudgetElementDto budgetElementToDto(BudgetElement budgetElement){
        return BudgetElementDto.builder()
                .category(budgetElement.getCategory().getName())
                .monetaryAmount(monetaryAmountToDto(budgetElement.getMonetaryAmount()))
                .build();
    }

    private MonetaryAmountDto monetaryAmountToDto(MonetaryAmount monetaryAmount){
        return MonetaryAmountDto.builder().amount(monetaryAmount.getAmount()).build();
    }
}
