package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.budgetModel.AchievementStatus;
import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.budgetModel.BudgetPlan;
import com.example.Analytics.logic.model.budgetModel.Category;
import com.example.Analytics.logic.model.generalModel.BankAccount;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import com.example.Analytics.logic.model.productModel.Duration;
import org.openapitools.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BudgetMapper {

    public FilterElement filterDtoToElement(ReportFilterDto reportFilterDto) {
        DurationDto durationDto = reportFilterDto.getDurationDto();
        List<String> categories = reportFilterDto.getCategories();
        List<UUID> bankAccountUUIDs = reportFilterDto.getBankAccounts();
        return FilterElement.builder()
                .duration(durationDto == null? null:durationDtoToDuration(durationDto))
                .categoryList(categories == null? new ArrayList<>(): stringListToCategories(categories))
                .bankAccountList(bankAccountUUIDs == null? new ArrayList<>(): uuidToBankAccount(bankAccountUUIDs))
                .build();
    }

    private List<BankAccount> uuidToBankAccount(List<UUID> uuidList){
        return uuidList.stream().map(uuid -> BankAccount.builder().id(uuid).build()).toList();
    }

    private List<Category> stringListToCategories(List<String> stringList){
        return stringList.stream().map(Category::new).toList();
    }
    private Duration durationDtoToDuration(DurationDto durationDto){
        return new Duration(durationDto.getStartTime(), durationDto.getEndTime());
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
