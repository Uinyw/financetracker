package com.financetracker.savingsgoal;

import org.apache.tomcat.util.digester.Rules;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Rule;
import org.openapitools.model.RuleBasedSavingsGoalDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RuleBasedSavingsGoalBuilder {
    public static RuleBasedSavingsGoalDTO buildWithDefaults() {
        String name = "testname";
        UUID id = UUID.randomUUID();
        String description = "testDescription";
        List<Rule> rules = buildWithDefault();
        RuleBasedSavingsGoalDTO.TypeEnum type = RuleBasedSavingsGoalDTO.TypeEnum.ANY;
        AchievementStatus achievementStatus = AchievementStatus.IN_PROGRESS;

        return build(name, id, description, rules, type, achievementStatus);
    }

    public static RuleBasedSavingsGoalDTO build(String name, UUID id, String description, List<Rule> rules, RuleBasedSavingsGoalDTO.TypeEnum type, AchievementStatus achievementStatus) {
        RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = new RuleBasedSavingsGoalDTO();
        ruleBasedSavingsGoalDTO.setRules(rules);
        ruleBasedSavingsGoalDTO.setType(type);
        ruleBasedSavingsGoalDTO.setName(name);
        ruleBasedSavingsGoalDTO.setDescription(description);
        ruleBasedSavingsGoalDTO.setId(id);
        ruleBasedSavingsGoalDTO.setAchievementStatus(achievementStatus);
        return ruleBasedSavingsGoalDTO;
    }

    public static List<Rule> buildWithDefault() {
        List<Rule> ruleList = new ArrayList<>();
        for (int i = 0; i < (int) Math.floor(Math.random() * 100); i++) {
            ruleList.add(buildRuleWithDefault());
        }
        return ruleList;
    }

    public static Rule buildRuleWithDefault() {
        UUID id = UUID.randomUUID();
        MonetaryAmount monetaryAmount = buildWIthDefault();
        String description = "test" + Math.floor(Math.random() * 100);
        Rule.TypeEnum type = Rule.TypeEnum.EQUALS;
        UUID bankAccountId = UUID.randomUUID();
        return build(id, monetaryAmount, description, type, bankAccountId);
    }

    public static Rule build(UUID id, MonetaryAmount monetaryAmount, String description, Rule.TypeEnum type, UUID bankAccountId) {
        Rule rule = new Rule();
        rule.setId(id);
        rule.setTarget(monetaryAmount);
        rule.setDescription(description);
        rule.setType(type);
        rule.setBankAccountID(bankAccountId);

        return rule;
    }

    public static MonetaryAmount buildWIthDefault() {
        return build(Math.floor(Math.random() * 1000000) / 10);
    }

    public static MonetaryAmount build(double money) {
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        monetaryAmount.setAmount(money);
        return monetaryAmount;
    }
}
