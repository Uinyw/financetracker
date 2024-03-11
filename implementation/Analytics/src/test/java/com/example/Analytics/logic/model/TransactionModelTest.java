package com.example.Analytics.logic.model;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import com.example.Analytics.logic.model.productModel.Consumption;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TransactionModelTest extends IntegrationTestBase {

    @Test
    void givenTransactionType_whenSetAttributes_thenAttributesAreUpdated(){
        TransactionType transactionType = TransactionType.INCOME;
        UUID id = UUID.randomUUID();
        double amount = 1.2;

        final Transaction transaction = Transaction.builder()
                .type(transactionType)
                .bankAccountTarget(id)
                .bankAccountTarget(id)
                .date(LocalDate.now())
                .referenceId(id)
                .amount(new MonetaryAmount(amount))
                .build();

        transactionType = TransactionType.EXPENSE;
        id = UUID.randomUUID();
        amount = 2;

        transaction.setId(id);
        transaction.setType(transactionType);
        transaction.setBankAccountTarget(id);
        transaction.setBankAccountSource(id);
        transaction.setDate(LocalDate.now());
        transaction.setReferenceId(id);
        transaction.setAmount(new MonetaryAmount(amount));
        transaction.setVariableMonthlyTransaction(new VariableMonthlyTransaction());

        assertThat(transaction.getType(), is(transactionType));
        assertThat(transaction.getBankAccountTarget(), is(id));
        assertThat(transaction.getBankAccountSource(), is(id));
        assertThat(transaction.getDate().toString(), is(LocalDate.now().toString()));
        assertThat(transaction.getId().toString(), is(id.toString()));
        assertThat(transaction.getReferenceId(), is(id));
    }
    @Test
    void givenFixedTransaction_whenSetAttributes_thenAttributesAreUpdated(){
        UUID id = UUID.randomUUID();
        Periodicity periodicity = Periodicity.MONTHLY;

        FixedTransaction fixedTransaction = FixedTransaction.builder()
                .periodicity(periodicity)
                .name("name 1")
                .referenceTransactions(List.of())
                .id(id)
                .category(new Category("category 1"))
                .build();

        TransactionType transactionType = TransactionType.INCOME;

        id = UUID.randomUUID();
        periodicity = Periodicity.HALF_YEARLY;

        fixedTransaction.setPeriodicity(periodicity);
        fixedTransaction.setName("name 2");
        fixedTransaction.setReferenceTransactions(List.of());
        fixedTransaction.setId(id);
        fixedTransaction.setCategory(new Category("category 2"));

        assertThat(fixedTransaction.getPeriodicity(), is(periodicity));
        assertThat(fixedTransaction.getReferenceTransactions().size(), is(0));
        assertThat(fixedTransaction.getName(), is("name 2"));
        assertThat(fixedTransaction.getCategory().getName(), is("category 2"));
        assertThat(fixedTransaction.getId(), is(id));
    }
    @Test
    void givenVariableMonthlyTransaction_whenSetAttributes_thenAttributesAreUpdated(){
        UUID id = UUID.randomUUID();
        Category category = new Category("uni");
        String name = "Variable Transaction";

        VariableMonthlyTransaction variableMonthlyTransaction = VariableMonthlyTransaction.builder()
                .id(id)
                .name(name)
                .category(category)
                .referenceTransactions(List.of())
                .build();

        id = UUID.randomUUID();
        category = new Category("shoes");
        name = "new Variable transaction";

        variableMonthlyTransaction.setCategory(category);
        variableMonthlyTransaction.setName(name);
        variableMonthlyTransaction.setReferenceTransactions(List.of());
        variableMonthlyTransaction.setId(id);
        variableMonthlyTransaction.setCategory(category);

        assertThat(variableMonthlyTransaction.getCategory().getName(), is(category.getName()));
        assertThat(variableMonthlyTransaction.getReferenceTransactions().size(), is(0));
        assertThat(variableMonthlyTransaction.getName(), is(name));
        assertThat(variableMonthlyTransaction.getCategory().getName(), is(category.getName()));
        assertThat(variableMonthlyTransaction.getId(), is(id));
    }
    @Test
    void givenBudgetPlan_whenSetAttributes_thenAttributesAreUpdated(){
        UUID id = UUID.randomUUID();
        AchievementStatus achievementStatus = AchievementStatus.ACHIEVED;

        BudgetElement budgetElement = BudgetElement.builder()
                .monetaryAmount(new MonetaryAmount(1))
                .category(new Category("cat 1"))
                .build();

        BudgetPlan budgetPlan = BudgetPlan.builder()
                .id(id)
                .budgetElementList(List.of(budgetElement))
                .currentStatus(achievementStatus)
                .startDate(LocalDate.now())
                .build();


        id = UUID.randomUUID();
        budgetElement = BudgetElement.builder()
                .monetaryAmount(new MonetaryAmount(2.0))
                .category(new Category("cat 2"))
                .build();
        achievementStatus = AchievementStatus.ACHIEVED;

        budgetPlan.setId(id);
        budgetPlan.setBudgetElementList(List.of(budgetElement));
        budgetPlan.setCurrentStatus(achievementStatus);
        budgetPlan.setStartDate(LocalDate.now());

        BudgetElement be = budgetPlan.getBudgetElementList().get(0);

        assertThat(budgetPlan.getId(), is(id));
        assertThat(budgetPlan.getStartDate(), is(LocalDate.now()));
        assertThat(budgetPlan.getBudgetElementList().size(), is(1));
        assertThat(budgetPlan.getCurrentStatus(), is(achievementStatus));
        assertThat(be.getMonetaryAmount().getAmount(), is(budgetElement.getMonetaryAmount().getAmount()));
        assertThat(be.getCategory().getName(), is(budgetElement.getCategory().getName()));
    }

}
