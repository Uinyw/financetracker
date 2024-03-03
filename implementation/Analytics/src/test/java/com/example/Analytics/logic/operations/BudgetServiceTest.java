package com.example.Analytics.logic.operations;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.db.FixedTransactionRepository;
import com.example.Analytics.infrastructure.db.ProductRepository;
import com.example.Analytics.infrastructure.db.VariableMonthlyTransactionRepository;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.model.productModel.Nutrition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openapitools.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BudgetServiceTest extends IntegrationTestBase {


    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired//@InjectMocks
    private BudgetService budgetService;
    //@Mock
    private VariableMonthlyTransactionService variableMonthlyTransactionService;
    @Autowired//@Mock
    private VariableMonthlyTransactionRepository variableMonthlyTransactionRepository;
    //@Mock
    private FixedTransactionService fixedTransactionService;
    @Mock
    private FixedTransactionRepository fixedTransactionRepository;

    @Test
    void testTransactionRecordDto_whenCreate_correctlyCreated(){
        TransferDto transferDto = TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("").build();
        TransactionRecordDto transactionRecordDto = TransactionRecordDto.builder().amount(new MonetaryAmountDto(5.0)).id(UUID.randomUUID()).transferStatus(TransferStatusDto.INITIAL).build();
        RecurringTransactionDto recurringTransactionDto = RecurringTransactionDto.builder()
                .fixedAmount(new MonetaryAmountDto(10.0))
                .id(UUID.randomUUID())
                .startDate(LocalDate.now().toString())
                .periodicity(PeriodicityDto.MONTHLY)
                .name("name")
                .transfer(transferDto)
                .labels(List.of("shoes", "uni"))
                .transactionRecords(List.of(transactionRecordDto))
                .type(TypeDto.EXPENSE)
                .description("description")
                .build();

        List<FixedTransaction> fixedTransactionList = transactionMapper.recurringMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto);

        budgetService.fixedTransactionChange(fixedTransactionList, UpdateType.CREATE);

        recurringTransactionDto.setLabels(List.of("uni", "food", "shoes"));
        recurringTransactionDto.setId(UUID.randomUUID());
        recurringTransactionDto.setTransactionRecords(List.of(TransactionRecordDto.builder().amount(new MonetaryAmountDto(5.0)).id(UUID.randomUUID()).transferStatus(TransferStatusDto.INITIAL).build()));
        fixedTransactionList = transactionMapper.recurringMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto);
        budgetService.fixedTransactionChange(fixedTransactionList, UpdateType.UPDATE);
        budgetService.fixedTransactionChange(fixedTransactionList, UpdateType.DELETE);

        final var variableMonthlyTransaction = VariableMonthlyTransaction.builder()
                .id(UUID.randomUUID())
                .name("Variable Transaction")
                .category(new Category("uni"))
                .build();

        final var transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .referenceId(UUID.randomUUID())
                .date(LocalDate.now())
                .bankAccountSource(UUID.randomUUID())
                .bankAccountTarget(UUID.randomUUID())
                .variableMonthlyTransaction(variableMonthlyTransaction)
                .amount(new MonetaryAmount(20.0))
                .type(TransactionType.EXPENSE)
                .build();

        variableMonthlyTransaction.appendTransaction(transaction);

        variableMonthlyTransactionRepository.save(variableMonthlyTransaction);
        BudgetPlan budgetPlan = budgetService.spendingForEachCategory(FilterElement.builder().bankAccountList(new ArrayList<>()).categoryList(new ArrayList<>(List.of(new Category("uni"), new Category("shoes")))).duration(new Duration(LocalDate.now().toString(), LocalDate.now().toString())).build());
        var x =1;
        //TODO have a look
    }

    @Test
    void testVariableMonthlyTransactionChange_whenUpdate_UpdateIsReceived() {
        final var variableMonthlyTransaction = VariableMonthlyTransaction.builder()
                .id(UUID.randomUUID())
                .name("Variable Transaction")
                .category(new Category("Food"))
                .build();

        final var transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .referenceId(UUID.randomUUID())
                .date(LocalDate.now())
                .bankAccountSource(UUID.randomUUID())
                .bankAccountTarget(UUID.randomUUID())
                .variableMonthlyTransaction(variableMonthlyTransaction)
                .amount(new MonetaryAmount(20.0))
                .type(TransactionType.EXPENSE)
                .build();

        variableMonthlyTransaction.appendTransaction(transaction);

        variableMonthlyTransactionRepository.save(variableMonthlyTransaction);

        OneTimeTransactionDto transactionDto = OneTimeTransactionDto.builder()
                .id(transaction.getReferenceId())
                .type(TypeDto.EXPENSE)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("").build())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(20.0).build())
                .labels(List.of("Food"))
                .build();

        List<VariableMonthlyTransaction> transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(transactionDto);

        budgetService.variableMonthlyTransactionChange(transactionList, UpdateType.DELETE);

        final var x = variableMonthlyTransactionRepository.findAll();
        assertEquals(0, x.size());

        transactionDto.setLabels(List.of("shoes", "toys"));
        transactionDto.setId(UUID.randomUUID());
        transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(transactionDto);
        budgetService.variableMonthlyTransactionChange(transactionList, UpdateType.CREATE);
        List<VariableMonthlyTransaction> variableMonthlyTransactionList = variableMonthlyTransactionRepository.findAll();
        assertEquals(2, variableMonthlyTransactionList.size());

        transactionDto.setLabels(List.of("test"));
        transactionDto.setId(UUID.randomUUID());
        transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(transactionDto);
        budgetService.variableMonthlyTransactionChange(transactionList, UpdateType.CREATE);
        variableMonthlyTransactionList = variableMonthlyTransactionRepository.findAll();
        assertEquals(3, variableMonthlyTransactionList.size());

        transactionDto.setLabels(List.of("changed labels"));
        transactionDto.setAmount(new MonetaryAmountDto(200000.0));
        transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(transactionDto);
        budgetService.variableMonthlyTransactionChange(transactionList, UpdateType.UPDATE);
        variableMonthlyTransactionList = variableMonthlyTransactionRepository.findAll();
        assertEquals(3, variableMonthlyTransactionList.size());

        transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(transactionDto);
        budgetService.variableMonthlyTransactionChange(transactionList, UpdateType.DELETE);
        variableMonthlyTransactionList = variableMonthlyTransactionRepository.findAll();
        assertEquals(2, variableMonthlyTransactionList.size());
    }
    @Test
    void testBudgetPlanChange_whenCreate_BudgetPlanIsCorrectlyCreated() {
        final var variableMonthlyTransaction = VariableMonthlyTransaction.builder()
                .id(UUID.randomUUID())
                .name("Variable Transaction")
                .category(new Category("Food"))
                .build();

        final var transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .referenceId(UUID.randomUUID())
                .date(LocalDate.now())
                .bankAccountSource(UUID.randomUUID())
                .bankAccountTarget(UUID.randomUUID())
                .variableMonthlyTransaction(variableMonthlyTransaction)
                .amount(new MonetaryAmount(20.0))
                .type(TransactionType.EXPENSE)
                .build();

        variableMonthlyTransaction.appendTransaction(transaction);

        variableMonthlyTransactionRepository.save(variableMonthlyTransaction);

        OneTimeTransactionDto transactionDto = OneTimeTransactionDto.builder()
                .id(transaction.getReferenceId())
                .type(TypeDto.EXPENSE)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("").build())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(200.0).build())
                .labels(List.of("Food", "uni", "shoes"))
                .build();

        List<VariableMonthlyTransaction> transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(transactionDto);
        budgetService.variableMonthlyTransactionChange(transactionList, UpdateType.CREATE);
        FilterElement filterElement = FilterElement.builder().bankAccountList(new ArrayList<>()).categoryList(new ArrayList<>(List.of(new Category("uni"), new Category("shoes")))).duration(new Duration(LocalDate.now().toString(), LocalDate.now().toString())).build();


        BudgetPlan budgetPlan = budgetService.createSavingsPlan(100, filterElement);

        var x = 0;
        assertEquals(3, budgetPlan.getBudgetElementList().size());
        assertEquals(80, budgetPlan.getBudgetElementList().get(budgetPlan.getBudgetElementList().size()-1).getMonetaryAmount().getAmount());
    }

    @Test
    void testBudgetPlan_whenGettingData_CorrectlyCreated() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double amountToBeSaved = 1200.0;
        BudgetElement budgetElement1 = createBudgetElement("name 1", 200.0);
        BudgetElement budgetElement2 = createBudgetElement("name 2", 400.0);

        BudgetPlan oneTimeBudgetPlan = createBudgetPlan(List.of(budgetElement1, budgetElement2), AchievementStatus.FAILED);
        BudgetPlan recurringBudgetPlan = createBudgetPlan(List.of(budgetElement1, budgetElement2), AchievementStatus.FAILED);
        FilterElement filterElement = FilterElement.builder().bankAccountList(new ArrayList<>()).categoryList(new ArrayList<>(List.of(new Category("name 1")))).duration(new Duration(LocalDate.now().toString(), LocalDate.now().toString())).build();

        Method createSavingsPlanMethod = BudgetService.class.getDeclaredMethod(
                "createSavingsPlan", double.class, BudgetPlan.class, BudgetPlan.class, FilterElement.class);
        createSavingsPlanMethod.setAccessible(true);
        BudgetPlan result = (BudgetPlan) createSavingsPlanMethod.invoke(budgetService, amountToBeSaved, oneTimeBudgetPlan, recurringBudgetPlan, filterElement);

        assertThat(result.getCurrentStatus().toString(), is(AchievementStatus.FAILED.toString()));
        assertThat(result.getBudgetElementList().size(), is(2));
        assertThat(result.getStartDate().toString(), is(LocalDate.now().toString()));

        amountToBeSaved = 200.0;
        result = (BudgetPlan) createSavingsPlanMethod.invoke(budgetService, amountToBeSaved, oneTimeBudgetPlan, recurringBudgetPlan, filterElement);

        assertThat(result.getCurrentStatus(), is(AchievementStatus.ACHIEVED));
        assertThat(result.getBudgetElementList().size(), is(2));
        assertThat(result.getStartDate().toString(), is(LocalDate.now().toString()));
    }

    @Test
    void testBudgetPlanWithoutFilter_whenGettingData_CorrectlyCreated() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        double amountToBeSaved = 1200.0;
        BudgetElement budgetElement1 = createBudgetElement("name 1", 200.0);
        BudgetElement budgetElement2 = createBudgetElement("name 2", 400.0);

        BudgetPlan oneTimeBudgetPlan = createBudgetPlan(List.of(budgetElement1, budgetElement2), AchievementStatus.FAILED);
        BudgetPlan recurringBudgetPlan = createBudgetPlan(List.of(budgetElement1, budgetElement2), AchievementStatus.FAILED);
        FilterElement filterElement = FilterElement.builder().bankAccountList(new ArrayList<>()).categoryList(new ArrayList<>(List.of(new Category("name 1"), new Category( "name 2")))).duration(new Duration(LocalDate.now().toString(), LocalDate.now().toString())).build();

        Method createSavingsPlanMethod = BudgetService.class.getDeclaredMethod(
                "createSavingsPlan", double.class, BudgetPlan.class, BudgetPlan.class, FilterElement.class);
        createSavingsPlanMethod.setAccessible(true);

        // Invoke the method on the budgetService instance
        BudgetPlan result = (BudgetPlan) createSavingsPlanMethod.invoke(budgetService, amountToBeSaved, oneTimeBudgetPlan, recurringBudgetPlan, filterElement);

        assertThat(result.getCurrentStatus(), is(AchievementStatus.ACHIEVED));
        assertThat(result.getBudgetElementList().size(), is(2));
        assertThat(result.getStartDate().toString(), is(LocalDate.now().toString()));

        amountToBeSaved = 200.0;
        filterElement = FilterElement.builder().bankAccountList(new ArrayList<>()).categoryList(new ArrayList<>()).duration(new Duration(LocalDate.now().toString(), LocalDate.now().toString())).build();
        result = (BudgetPlan) createSavingsPlanMethod.invoke(budgetService, amountToBeSaved, oneTimeBudgetPlan, recurringBudgetPlan, filterElement);

        assertThat(result.getCurrentStatus(), is(AchievementStatus.ACHIEVED));
        assertThat(result.getBudgetElementList().size(), is(4));
        assertThat(result.getStartDate().toString(), is(LocalDate.now().toString()));
    }

    @Test
    void testBudgetPlanSpendingForCategories_whenGettingData_CorrectlyCreated() throws NoSuchFieldException, IllegalAccessException {
        FilterElement filterElement = FilterElement.builder().bankAccountList(new ArrayList<>()).categoryList(new ArrayList<>(List.of(new Category("name 1"), new Category( "name 2")))).duration(new Duration(LocalDate.now().toString(), LocalDate.now().toString())).build();
        BudgetPlan budgetPlan = budgetService.spendingForEachCategory(filterElement);


        assertThat(budgetPlan.getCurrentStatus(), is(AchievementStatus.ACHIEVED));
        assertThat(budgetPlan.getBudgetElementList().size(), is(0));
        assertThat(budgetPlan.getStartDate().toString(), is(LocalDate.now().toString()));

        TransactionType transactionType = TransactionType.INCOME;
        Transaction transaction1 = createTransaction(transactionType, UUID.randomUUID(), 200);
        Transaction transaction2 = createTransaction(transactionType, UUID.randomUUID(), 100);

        List<Transaction> referenceTransactions = List.of(transaction2,transaction1);
        UUID referenceId = UUID.randomUUID();
        VariableMonthlyTransaction newTransaction = createVariableMonthlyTransaction(referenceTransactions, referenceId);

        Field variableTransactionHistoryField = BudgetService.class.getDeclaredField("variableTransactionHistory");
        variableTransactionHistoryField.setAccessible(true);
        ArrayList<VariableMonthlyTransaction> variableTransactionHistory = (ArrayList<VariableMonthlyTransaction>) variableTransactionHistoryField.get(budgetService);
        variableTransactionHistory.add(newTransaction);
        variableTransactionHistoryField.set(budgetService, variableTransactionHistory);

        filterElement = FilterElement.builder().bankAccountList(new ArrayList<>()).categoryList(new ArrayList<>(List.of())).duration(new Duration(LocalDate.now().toString(), LocalDate.now().toString())).build();
        budgetPlan = budgetService.spendingForEachCategory(filterElement);

        assertThat(budgetPlan.getCurrentStatus(), is(AchievementStatus.ACHIEVED));
        assertThat(budgetPlan.getBudgetElementList().size(), is(3));
        assertThat(budgetPlan.getBudgetElementList().get(0).getMonetaryAmount().getAmount(), is(300.0));
        assertThat(budgetPlan.getStartDate().toString(), is(LocalDate.now().toString()));
    }

    @AfterEach
    void tearDown() {
        variableMonthlyTransactionRepository.deleteAll();
        fixedTransactionRepository.deleteAll();
    }

    private VariableMonthlyTransaction createVariableMonthlyTransaction(List<Transaction> referenceTransactions, UUID referenceId){
        VariableMonthlyTransaction monthlyTransaction = VariableMonthlyTransaction.builder()
                .referenceTransactions(referenceTransactions)
                .id(referenceId)
                .category(new Category("test" + Math.round(Math.random()*3)/3.0))
                .name("variable monthly Transaction")
                .build();
        monthlyTransaction.setReferenceTransactions(referenceTransactions);
        return monthlyTransaction;
    }

    private Transaction createTransaction(TransactionType transactionType, UUID id, double amount){
        return Transaction.builder()
                .type(transactionType)
                .bankAccountTarget(id)
                .bankAccountTarget(id)
                .date(LocalDate.now())
                .referenceId(id)
                .amount(new MonetaryAmount(amount))
                .build();
    }


    private OneTimeTransactionDto createOneTimeTransactionDto(UUID uuid, TypeDto type, List<String> labels, TransferDto transfer, MonetaryAmountDto amountDto, String date, TransferStatusDto transferStatus){
        return OneTimeTransactionDto.builder()
                .id(uuid)
                .name("Transaction Name")
                .description("Transaction Description")
                .type(type)
                .labels(labels)
                .transfer(transfer)
                .amount(amountDto)
                .transferStatus(transferStatus)
                .date(date)
                .build();
    }

    private TransferDto createTransferDto(UUID externalSourceId, UUID targetBankAccountId, UUID sourceBankAccountId, UUID externalTargetId){
        return TransferDto.builder()
                .externalSourceId(externalSourceId.toString())
                .externalTargetId(externalTargetId.toString())
                .targetBankAccountId(targetBankAccountId)
                .sourceBankAccountId(sourceBankAccountId)
                .build();
    }

    private BudgetPlan createBudgetPlan(List<BudgetElement> budgetElements, AchievementStatus currentStatus){
        return BudgetPlan.builder()
                .id(UUID.randomUUID())
                .startDate(LocalDate.now())
                .currentStatus(currentStatus)
                .budgetElementList(budgetElements)
                .build();
    }

    private BudgetElement createBudgetElement(String name, double amount){
        return BudgetElement.builder()
                .category(new Category(name))
                .monetaryAmount(new MonetaryAmount(amount))
                .build();
    }
}
