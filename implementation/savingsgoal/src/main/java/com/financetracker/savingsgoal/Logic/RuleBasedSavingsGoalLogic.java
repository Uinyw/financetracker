package com.financetracker.savingsgoal.Logic;

import com.financetracker.savingsgoal.MonetaryAmount;
import com.financetracker.savingsgoal.Rule;
import com.financetracker.savingsgoal.RuleBasedSavingsGoal;
import com.financetracker.savingsgoal.model.RuleBasedSavingsGoalMapper;
import com.financetracker.savingsgoal.model.RuleBasedSavingsGoalRepository;
import com.financetracker.savingsgoal.client.BankAccountClient;
import com.financetracker.savingsgoal.model.SavingsGoalMapper;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RuleBasedSavingsGoalLogic {

    private final BankAccountClient bankAccountClient;
    private final RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository;
    private final RuleBasedSavingsGoalMapper ruleBasedSavingsGoalMapper;

    public RuleBasedSavingsGoalLogic(BankAccountClient bankAccountClient, RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository, RuleBasedSavingsGoalMapper ruleBasedSavingsGoalMapper) {
        this.bankAccountClient = bankAccountClient;
        this.ruleBasedSavingsGoalRepository = ruleBasedSavingsGoalRepository;
        this.ruleBasedSavingsGoalMapper = ruleBasedSavingsGoalMapper;
    }


    public List<RuleBasedSavingsGoalDTO> getRuleBasedSavingsGoals() {
        System.out.println("getting all the rule based savings goals");
        List<RuleBasedSavingsGoalDTO> ruleBasedSavingsGoalDTOS = new ArrayList<>();
        ruleBasedSavingsGoalRepository.findAll().forEach(rbsg
                -> ruleBasedSavingsGoalDTOS.add(ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalEntityToDTO(rbsg)));
        return ruleBasedSavingsGoalDTOS;
    }

    public boolean createRuleBasedSavingsGoal(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO) {
        ruleBasedSavingsGoalRepository.save(ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalDTOtoEntity(ruleBasedSavingsGoalDTO));
        System.out.println("rule based savings goal has been created");
        return true;
    }

    public boolean deleteRuleBasedSavingsGoal(String id) {
        RuleBasedSavingsGoal ruleBasedSavingsGoal = findRuleBasedSavingsGoalById(id);
        if (ruleBasedSavingsGoal == null) return false;

        ruleBasedSavingsGoalRepository.delete(ruleBasedSavingsGoal);
        return true;
    }

    public RuleBasedSavingsGoalDTO getRuleBasedSavingsGoal(String id) {
        RuleBasedSavingsGoal ruleBasedSavingsGoal = findRuleBasedSavingsGoalById(id);
        return ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalEntityToDTO(ruleBasedSavingsGoal);
    }

    private RuleBasedSavingsGoal findRuleBasedSavingsGoalById(String id) {
        for (RuleBasedSavingsGoal sg : ruleBasedSavingsGoalRepository.findAll()) {
            if (sg.getId().toString().equals(id)) return sg;
        }
        return null;
    }

    public RuleBasedSavingsGoal matchRuleBasedSavingsGoal(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        boolean rulesMatch = false;
        switch (ruleBasedSavingsGoal.getMatchingType()) {
            case MATCH_ALL -> rulesMatch = matchAllRules(ruleBasedSavingsGoal);
            case MATCH_ANY -> rulesMatch = matchAnyRule(ruleBasedSavingsGoal);

        }
        if (rulesMatch) ruleBasedSavingsGoal.setAchievementStatus(AchievementStatus.ACHIEVED);
        if (!rulesMatch) ruleBasedSavingsGoal.setAchievementStatus(AchievementStatus.FAILED);

        return ruleBasedSavingsGoal;
    }

    private boolean matchAnyRule(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        boolean match = false;
        for (Rule rule : ruleBasedSavingsGoal.getRules()) {
            if (matchRule(rule)) return true;
        }
        return match;
    }

    private boolean matchAllRules(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        boolean match = true;
        for (Rule rule : ruleBasedSavingsGoal.getRules()) {
            if (!matchRule(rule)) match = false;
        }
        return match;
    }

    private boolean matchRule(Rule rule) {
        //get BankAccount
        double money = getBankAccountBalance(rule.getBankAccountID().toString());
        return matchRuleType(rule, money);
    }

    private boolean matchRuleType(Rule rule, double money) {
        boolean match = false;
        MonetaryAmount targetAmount = rule.getTarget();
        switch (rule.getRuleType()) {
            case GREATER_THAN -> match = money > targetAmount.getAmount();
            case EQUALS -> match = money == targetAmount.getAmount();
            case LESS_THAN -> match = money < targetAmount.getAmount();
        }
        return match;
    }

    private void updateRuleBasedSavingsGoal(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        this.ruleBasedSavingsGoalRepository.delete(ruleBasedSavingsGoal);
        this.ruleBasedSavingsGoalRepository.save(ruleBasedSavingsGoal);
    }

    public void checkForChanges(BankAccountDto bankAccountDto) {
        List<RuleBasedSavingsGoal> lRbsg = ruleBasedSavingsGoalRepository.findAll();
        lRbsg = lRbsg.stream().filter(
                rbsg -> rbsg.getRules().stream().anyMatch(
                        x -> x.getBankAccountID().equals(bankAccountDto.getId())
                )).toList();
        lRbsg.stream().map(this::matchRuleBasedSavingsGoal);
        lRbsg.forEach(this::updateRuleBasedSavingsGoal);
        System.out.println("changes checked");
    }

    private double getBankAccountBalance(String id) {
        Optional<BankAccountDto> bankAccountDto = bankAccountClient.getBankAccount(id);
        double money = 0.0;

        if (bankAccountDto.isEmpty()) { //TODO what to do if the bank account doesn't exist
            System.out.println("bank account does not exist");
            return money;
        }
        BankAccountDto bankAccount = bankAccountDto.get();
        MonetaryAmountDto balance = bankAccount.getBalance();
        try {
            money = balance.getAmount();
            return money;
        } catch (Exception e) {
            System.out.println("No Monetary amount provided");
        }
        return money;
    }


}
