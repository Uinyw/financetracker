package com.financetracker.savingsgoal.logic.operations;

import com.financetracker.savingsgoal.logic.model.RuleBasedSavingsGoal;
import com.financetracker.savingsgoal.infrastructure.db.RuleBasedSavingsGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RuleBasedSavingsGoalLogic {

    private final RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository;
    private final RuleBasedSavingsGoalMatchingLogic ruleBasedSavingsGoalMatchingLogic;

    public List<RuleBasedSavingsGoal> getRuleBasedSavingsGoals() {
        return ruleBasedSavingsGoalRepository.findAll();
    }

    public void createRuleBasedSavingsGoal(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        ruleBasedSavingsGoalRepository.save(ruleBasedSavingsGoal);
        ruleBasedSavingsGoalMatchingLogic.checkForChanges(ruleBasedSavingsGoal);
    }

    public boolean updateRuleBasedSavingsGoal(final String savingsGoalId, final RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        final var originalSavingsGoal = findRuleBasedSavingsGoalById(savingsGoalId);
        if (originalSavingsGoal == null) {
            return false;
        }

        ruleBasedSavingsGoalRepository.save(ruleBasedSavingsGoal);
        ruleBasedSavingsGoalMatchingLogic.checkForChanges(ruleBasedSavingsGoal);
        return true;
    }

    public boolean deleteRuleBasedSavingsGoal(String id) {
        RuleBasedSavingsGoal ruleBasedSavingsGoal = findRuleBasedSavingsGoalById(id);
        if (ruleBasedSavingsGoal == null)
            return false;

        ruleBasedSavingsGoalRepository.delete(ruleBasedSavingsGoal);
        return true;
    }

    public RuleBasedSavingsGoal findRuleBasedSavingsGoalById(String id) {
        for (RuleBasedSavingsGoal sg : ruleBasedSavingsGoalRepository.findAll()) {
            if (sg.getId().toString().equals(id)) return sg;
        }
        return null;
    }




}
