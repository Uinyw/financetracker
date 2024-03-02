package com.example.Analytics.logic.api.mapping;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.BudgetMapper;
import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.logic.model.budgetModel.AchievementStatus;
import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.budgetModel.BudgetPlan;
import com.example.Analytics.logic.model.budgetModel.Category;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.*;
import org.openapitools.model.BudgetAchievementStatusDto;
import org.openapitools.model.BudgetElementDto;
import org.openapitools.model.BudgetPlanDto;
import org.openapitools.model.ReportFilterDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AnalyticsBudgetMapperMapperTest extends IntegrationTestBase {
    @Autowired
    private BudgetMapper budgetMapper;

    @Test
    void givenBudgetPlan_whenMap_thenBudgetPlanDtoExists() {
        UUID id = UUID.randomUUID();
        AchievementStatus currentStatus = AchievementStatus.ACHIEVED;
        BudgetElement budgetElement = createBudgetElement(new MonetaryAmount(0.0), new Category("test"));
        List<BudgetElement> budgetElementList = List.of(budgetElement);
        LocalDate startDate = LocalDate.now();

        BudgetPlan budgetPlan = createBudgetPlan(id, currentStatus, budgetElementList, startDate);
        BudgetPlanDto budgetPlanDto = budgetMapper.budgetPlanToDto(budgetPlan);
        BudgetElementDto budgetElementDto = budgetPlanDto.getPlan().get(0);

        assertThat(budgetPlan.getId(), is(budgetPlanDto.getId()));
        assertThat(budgetPlan.getStartDate().toString(), is(budgetPlanDto.getDate()));
        assertThat(budgetPlan.getCurrentStatus().toString(), is(budgetPlanDto.getAchievementStatus().getValue()));
        assertThat(budgetElement.getMonetaryAmount().getAmount(), is(budgetElementDto.getMonetaryAmount().getAmount()));
        assertThat(budgetElement.getCategory().getName(), is(budgetElementDto.getCategory()));
    }

    @Test
    void givenReportFilterDto_whenMap_thenFilterElementExists() {
        ReportFilterDto reportFilterDto = ReportFilterDto.builder().build();
        FilterElement filterElement = budgetMapper.filterDtoToElement(reportFilterDto);

        assertThat(null, is(filterElement));
    }

private BudgetElement createBudgetElement(MonetaryAmount monetaryAmount, Category category){
        return BudgetElement.builder()
                .monetaryAmount(monetaryAmount)
                .category(category)
                .build();
}
    private BudgetPlan createBudgetPlan(UUID id, AchievementStatus currentStatus, List<BudgetElement> budgetElementList, LocalDate startDate){
        return BudgetPlan.builder()
                .id(id)
                .currentStatus(currentStatus)
                .budgetElementList(budgetElementList)
                .startDate(startDate)
                .build();
    }
}
