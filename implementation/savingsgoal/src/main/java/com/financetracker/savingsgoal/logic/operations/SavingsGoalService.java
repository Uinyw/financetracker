package com.financetracker.savingsgoal.logic.operations;

import java.util.List;

import com.financetracker.savingsgoal.logic.model.PeriodicalSavingsGoal;
import com.financetracker.savingsgoal.logic.model.RuleBasedSavingsGoal;
import org.openapitools.client.model.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingsGoalService {

    private final RuleBasedSavingsGoalLogic ruleBasedSavingsGoalLogic;
    private final RuleBasedSavingsGoalMatchingLogic matchingLogic;
    private final PeriodicalSavingsGoalLogic periodicalSavingsGoalLogic;

    public void onReceiveBankAccountChange(BankAccountDto bankAccountDto) {
        matchingLogic.checkForChanges(bankAccountDto);
    }

    public List<PeriodicalSavingsGoal> getPeriodicalSavingsGoals() {
        return periodicalSavingsGoalLogic.getPeriodicalSavingsGoals();
    }

    public void createPeriodicalSavingsGoal(PeriodicalSavingsGoal periodicalSavingsGoal) {
        periodicalSavingsGoalLogic.createPeriodicalSavingsGoal(periodicalSavingsGoal);
    }

    public boolean updatePeriodicalSavingsGoal(String savingsGoalId, PeriodicalSavingsGoal periodicalSavingsGoal) {
        return periodicalSavingsGoalLogic.updatePeriodicalSavingsGoal(savingsGoalId, periodicalSavingsGoal);
    }

    public boolean deletePeriodicalSavingsGoal(String id) {
        return periodicalSavingsGoalLogic.deletePeriodicalSavingsGoal(id);
    }

    public PeriodicalSavingsGoal getPeriodicalSavingsGoal(String id) {
        return periodicalSavingsGoalLogic.findPeriodicalSavingsGoalById(id);
    }

    public List<RuleBasedSavingsGoal> getRuleBasedSavingsGoals() {
        return ruleBasedSavingsGoalLogic.getRuleBasedSavingsGoals();
    }

    public void createRuleBasedSavingsGoal(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        ruleBasedSavingsGoalLogic.createRuleBasedSavingsGoal(ruleBasedSavingsGoal);
    }

    public boolean updateRuleBasedSavingsGoal(String savingsGoalId, RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        return ruleBasedSavingsGoalLogic.updateRuleBasedSavingsGoal(savingsGoalId, ruleBasedSavingsGoal);
    }

    public boolean deleteRuleBasedSavingsGoal(String id) {
        return ruleBasedSavingsGoalLogic.deleteRuleBasedSavingsGoal(id);
    }

    public RuleBasedSavingsGoal getRuleBasedSavingsGoal(String id) {
        return ruleBasedSavingsGoalLogic.findRuleBasedSavingsGoalById(id);
    }
}
