package com.financetracker.savingsgoal.logic.operations;

import com.financetracker.savingsgoal.infrastructure.db.RuleBasedSavingsGoalRepository;
import com.financetracker.savingsgoal.logic.model.AchievementStatus;
import com.financetracker.savingsgoal.logic.model.MonetaryAmount;
import com.financetracker.savingsgoal.logic.model.Rule;
import com.financetracker.savingsgoal.logic.model.RuleBasedSavingsGoal;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.BankAccountDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RuleBasedSavingsGoalMatchingLogic {

    private final RuleBasedSavingsGoalRepository repository;

    public void checkForChanges(BankAccountDto bankAccountDto) {
        repository.findAll().stream()
                .filter(ruleBasedSavingsGoal -> savingsGoalIsBasedOnBankAccount(ruleBasedSavingsGoal, bankAccountDto.getId()))
                .forEach(ruleBasedSavingsGoal -> {
                    final var isSavingsGoalAchieved = isSavingsGoalAchieved(ruleBasedSavingsGoal, bankAccountDto);
                    ruleBasedSavingsGoal.setAchievementStatus(isSavingsGoalAchieved ? AchievementStatus.ACHIEVED : AchievementStatus.FAILED);
                    repository.save(ruleBasedSavingsGoal);
                });
    }

    private boolean savingsGoalIsBasedOnBankAccount(final RuleBasedSavingsGoal ruleBasedSavingsGoal, final UUID bankAccountId) {
        return ruleBasedSavingsGoal.getRules().stream()
                .anyMatch(x -> x.getBankAccountId().equals(bankAccountId));
    }

    public boolean isSavingsGoalAchieved(RuleBasedSavingsGoal ruleBasedSavingsGoal, final BankAccountDto bankAccountDto) {
       return switch (ruleBasedSavingsGoal.getMatchingType()) {
            case MATCH_ALL -> matchAllRules(ruleBasedSavingsGoal, bankAccountDto);
            case MATCH_ANY -> matchAnyRule(ruleBasedSavingsGoal, bankAccountDto);
        };
    }

    private boolean matchAllRules(final RuleBasedSavingsGoal ruleBasedSavingsGoal, final BankAccountDto bankAccountDto) {
        return ruleBasedSavingsGoal.getRules().stream()
                .filter(x -> x.getBankAccountId().equals(bankAccountDto.getId()))
                .allMatch(rule -> matchRule(rule, bankAccountDto));
    }

    private boolean matchAnyRule(final RuleBasedSavingsGoal ruleBasedSavingsGoal, final BankAccountDto bankAccountDto) {
        return ruleBasedSavingsGoal.getRules().stream()
                .filter(x -> x.getBankAccountId().equals(bankAccountDto.getId()))
                .anyMatch(rule -> matchRule(rule, bankAccountDto));
    }

    private boolean matchRule(final Rule rule, final BankAccountDto bankAccountDto) {
        return matchRuleType(rule, bankAccountDto.getBalance().getAmount());
    }

    private boolean matchRuleType(Rule rule, double money) {
        MonetaryAmount targetAmount = rule.getTarget();
        return switch (rule.getRuleType()) {
            case GREATER_THAN -> money > targetAmount.getAmount();
            case EQUALS -> money == targetAmount.getAmount();
            case LESS_THAN -> money < targetAmount.getAmount();
        };
    }
}
