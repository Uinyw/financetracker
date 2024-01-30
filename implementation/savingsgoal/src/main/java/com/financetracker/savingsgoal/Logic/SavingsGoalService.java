package com.financetracker.savingsgoal.Logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.financetracker.savingsgoal.Duration;
import com.financetracker.savingsgoal.model.SavingsGoalMapper;
import org.openapitools.client.model.*;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingsGoalService {

    private final RuleBasedSavingsGoalLogic ruleBasedSavingsGoalLogic;
    private final PeriodicalSavingsGoalLogic periodicalSavingsGoalLogic;

    public void receivedNewTransaction(BankAccountDto bankAccountDto) {
        ruleBasedSavingsGoalLogic.checkForChanges(bankAccountDto);
    }
    public List<PeriodicalSavingsGoalDTO> getPeriodicalSavingsGoals() {
        return periodicalSavingsGoalLogic.getPeriodicalSavingsGoals();
    }

    public boolean createPeriodicalSavingsGoal(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        return periodicalSavingsGoalLogic.createPeriodicalSavingsGoal(periodicalSavingsGoalDTO);
    }

    public boolean deletePeriodicalSavingsGoal(String id) {
        return periodicalSavingsGoalLogic.deletePeriodicalSavingsGoal(id);
    }

    public PeriodicalSavingsGoalDTO getPeriodicalSavingsGoal(String id) {
        return periodicalSavingsGoalLogic.getPeriodicalSavingsGoal(id);
    }

    public List<RuleBasedSavingsGoalDTO> getRuleBasedSavingsGoals() {
        return ruleBasedSavingsGoalLogic.getRuleBasedSavingsGoals();
    }

    public boolean createRuleBasedSavingsGoal(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO) {
        return ruleBasedSavingsGoalLogic.createRuleBasedSavingsGoal(ruleBasedSavingsGoalDTO);
    }

    public boolean deleteRuleBasedSavingsGoal(String id) {
        return ruleBasedSavingsGoalLogic.deleteRuleBasedSavingsGoal(id);
    }

    public RuleBasedSavingsGoalDTO getRuleBasedSavingsGoal(String id) {
        return ruleBasedSavingsGoalLogic.getRuleBasedSavingsGoal(id);
    }

    //TODO delete
    public PeriodicalSavingsGoalDTO createRandomPeriodicalSavingsGoal() {
        com.financetracker.savingsgoal.MonetaryAmount newMonetaryAmount = new com.financetracker.savingsgoal.MonetaryAmount();
        newMonetaryAmount.setAmount(Math.random() * 10000);
        Duration duration = new Duration();
        duration.setStart(LocalDate.now());
        duration.setEnd(LocalDate.of(2025, 10, 5));

        final var periodicalSavingsGoalDTO = new PeriodicalSavingsGoalDTO();
        periodicalSavingsGoalDTO.setId(UUID.randomUUID());
        periodicalSavingsGoalDTO.setName("random Name");
        periodicalSavingsGoalDTO.setDescription("random description");
        periodicalSavingsGoalDTO.setAchievementStatus(AchievementStatus.ACHIEVED);
        periodicalSavingsGoalDTO.setBankAccountID(UUID.randomUUID());
        periodicalSavingsGoalDTO.setGoal(SavingsGoalMapper.monetaryAmountModeltoDTO(newMonetaryAmount));
        periodicalSavingsGoalDTO.setRecurringRate(newMonetaryAmount.getAmount());
        periodicalSavingsGoalDTO.setRecurringAmount(SavingsGoalMapper.monetaryAmountModeltoDTO(newMonetaryAmount));
        periodicalSavingsGoalDTO.setDuration(SavingsGoalMapper.durationToString(duration));
        periodicalSavingsGoalDTO.setPeriodicity(org.openapitools.model.Periodicity.MONTHLY);
        periodicalSavingsGoalDTO.setOneTimeTransactionRecord(new ArrayList<>());
        return periodicalSavingsGoalDTO;
    }

    //TODO delete
    public RuleBasedSavingsGoalDTO createRandomRBasedSavingsGoal() {
        RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = new RuleBasedSavingsGoalDTO();
        ruleBasedSavingsGoalDTO.setId(UUID.randomUUID());
        ruleBasedSavingsGoalDTO.setName("name");
        ruleBasedSavingsGoalDTO.setDescription("description");
        ruleBasedSavingsGoalDTO.setType(RuleBasedSavingsGoalDTO.TypeEnum.ANY);
        ruleBasedSavingsGoalDTO.setRules(new ArrayList<>());
        return ruleBasedSavingsGoalDTO;
    }
}
