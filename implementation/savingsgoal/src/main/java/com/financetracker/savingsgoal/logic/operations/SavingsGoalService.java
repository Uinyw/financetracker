package com.financetracker.savingsgoal.logic.operations;

import java.util.List;

import org.openapitools.client.model.*;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingsGoalService {

    private final RuleBasedSavingsGoalLogic ruleBasedSavingsGoalLogic;
    private final PeriodicalSavingsGoalLogic periodicalSavingsGoalLogic;

    public void onReceiveBankAccountChange(BankAccountDto bankAccountDto) {
        ruleBasedSavingsGoalLogic.checkForChanges(bankAccountDto);
    }
    public List<PeriodicalSavingsGoalDTO> getPeriodicalSavingsGoals() {
        return periodicalSavingsGoalLogic.getPeriodicalSavingsGoals();
    }

    public void createPeriodicalSavingsGoal(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        periodicalSavingsGoalLogic.createPeriodicalSavingsGoal(periodicalSavingsGoalDTO);
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

    public void createRuleBasedSavingsGoal(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO) {
        ruleBasedSavingsGoalLogic.createRuleBasedSavingsGoal(ruleBasedSavingsGoalDTO);
    }

    public boolean deleteRuleBasedSavingsGoal(String id) {
        return ruleBasedSavingsGoalLogic.deleteRuleBasedSavingsGoal(id);
    }

    public RuleBasedSavingsGoalDTO getRuleBasedSavingsGoal(String id) {
        return ruleBasedSavingsGoalLogic.getRuleBasedSavingsGoal(id);
    }
}
