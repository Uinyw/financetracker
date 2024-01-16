package com.financetracker.savingsgoal.model;

import com.financetracker.savingsgoal.*;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.Periodicity;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class SavingsGoalMapper {

    public PeriodicalSavingsGoalDTO periodicalSavingsGoalEntityToDTO(PeriodicalSavingsGoal periodicalSavingsGoal){
        String duration = durationToString(periodicalSavingsGoal.getDuration());

        return createPeriodicalSavingsGoalDTO(periodicalSavingsGoal.getId(),
                periodicalSavingsGoal.getName(),
                periodicalSavingsGoal.getDescription(),
                periodicalSavingsGoal.getAchievementStatus(),
                periodicalSavingsGoal.getBankAccountId(),
                periodicalSavingsGoal.getGoal(),
                periodicalSavingsGoal.getRecurringRate().getAmount(),
                periodicalSavingsGoal.getRecurringAmount(),
                duration,
                periodicalSavingsGoal.getPeriodicity());
    }
    public PeriodicalSavingsGoal periodicalSavingsGoalDTOtoEntity(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO){
        //TODO more work required (savings records missmatch)
        org.openapitools.model.MonetaryAmount reoccuringRate = new org.openapitools.model.MonetaryAmount();
        reoccuringRate.setAmount(periodicalSavingsGoalDTO.getRecurringRate());

        Duration duration = stringToDuration(periodicalSavingsGoalDTO.getDuration());

        return createPeriodicalSavingsGoal(periodicalSavingsGoalDTO.getBankAccountID(),
                reoccuringRate,
                duration,
                convertDTOsavingsRectordToModel(periodicalSavingsGoalDTO.getSavingsRecord()),
                monetaryAmountDTOtoModel(periodicalSavingsGoalDTO.getRecurringAmount()),
                monetaryAmountDTOtoModel(periodicalSavingsGoalDTO.getGoal()));
    }

    public RuleBasedSavingsGoalDTO ruleBasedSavingsGoalEntityToDTO(RuleBasedSavingsGoal ruleBasedSavingsGoal){
        if(ruleBasedSavingsGoal==null)
            return new RuleBasedSavingsGoalDTO();

        RuleBasedSavingsGoalDTO.TypeEnum matchingType = RuleBasedSavingsGoalDTO.TypeEnum.ALL;
        switch (ruleBasedSavingsGoal.getMatchingType()){
            case MATCH_ALL -> matchingType = RuleBasedSavingsGoalDTO.TypeEnum.ANY;
            case MATCH_ANY -> matchingType = RuleBasedSavingsGoalDTO.TypeEnum.ALL;
        }

        return createRuleBasedSavingsGoalDTO(
                ruleBasedSavingsGoal.getId(),
                ruleBasedSavingsGoal.getName(),
                ruleBasedSavingsGoal.getDescription(),
                ruleBasedSavingsGoal.getBankAccountIds(),
                matchingType,
                ruleBasedSavingsGoal.getRules());
    }
    public RuleBasedSavingsGoal ruleBasedSavingsGoalDTOtoEntity(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO){

        MatchingType typeEnum = MatchingType.MATCH_ALL;
        switch (ruleBasedSavingsGoalDTO.getType()){
            case ALL -> typeEnum = MatchingType.MATCH_ALL;
            case ANY -> typeEnum = MatchingType.MATCH_ANY;
        }

        RuleBasedSavingsGoal ruleBasedSavingsGoal = createRuleBasedSavingsGoal(
                ruleBasedSavingsGoalDTO.getId(),
                ruleBasedSavingsGoalDTO.getDescription(),
                ruleBasedSavingsGoalDTO.getName(),
                ruleBasedSavingsGoalDTO.getAchievementStatus(),
                ruleBasedSavingsGoalDTO.getBankAccountIds(),
                convertRuleDTOToModel(ruleBasedSavingsGoalDTO.getRules()),
                typeEnum
        );

        return ruleBasedSavingsGoal;
    }


    public static PeriodicalSavingsGoalDTO createPeriodicalSavingsGoalDTO(UUID id, String name, String description, AchievementStatus achievementStatus, UUID bankAccountId, MonetaryAmount goal, double reoccuringRate, MonetaryAmount reoccuringAmount, String duration, Periodicity periodicity){
        PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO = new PeriodicalSavingsGoalDTO();

        periodicalSavingsGoalDTO.setId(id);
        periodicalSavingsGoalDTO.setName(name);
        periodicalSavingsGoalDTO.setDescription(description);
        periodicalSavingsGoalDTO.setAchievementStatus(achievementStatus);
        periodicalSavingsGoalDTO.setBankAccountID(bankAccountId);
        periodicalSavingsGoalDTO.setGoal(monetaryAmountModeltoDTO(goal));
        periodicalSavingsGoalDTO.setRecurringRate(reoccuringRate);
        periodicalSavingsGoalDTO.setRecurringAmount(monetaryAmountModeltoDTO(reoccuringAmount));
        periodicalSavingsGoalDTO.setDuration(duration);
        periodicalSavingsGoalDTO.setPeriodicity(periodicity);
        return periodicalSavingsGoalDTO;
    }

    public static PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID bankAccountId, org.openapitools.model.MonetaryAmount reoccuringRate, Duration duration, List<SavingsRecord> records, MonetaryAmount reoccuringAmount, MonetaryAmount goal){
        PeriodicalSavingsGoal periodicalSavingsGoal = new PeriodicalSavingsGoal();
        periodicalSavingsGoal.setBankAccountId(bankAccountId);
        periodicalSavingsGoal.setRecurringRate(monetaryAmountDTOtoModel(reoccuringRate));
        periodicalSavingsGoal.setDuration(duration);
        periodicalSavingsGoal.setRecords(records);
        periodicalSavingsGoal.setRecurringAmount(reoccuringAmount);
        periodicalSavingsGoal.setGoal(goal);
        return periodicalSavingsGoal;
    }

    private static MonetaryAmount monetaryAmountDTOtoModel(org.openapitools.model.MonetaryAmount monetaryAmount){
        MonetaryAmount monetaryAmountModel = new MonetaryAmount();
        monetaryAmountModel.setAmount(monetaryAmount.getAmount());
        return monetaryAmountModel;
    }

    public static org.openapitools.model.MonetaryAmount monetaryAmountModeltoDTO(MonetaryAmount monetaryAmount){
        org.openapitools.model.MonetaryAmount monetaryAmountModel = new org.openapitools.model.MonetaryAmount();
        monetaryAmountModel.setAmount(monetaryAmount.getAmount());
        return monetaryAmountModel;
    }

    public static String durationToString(Duration duration){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );
        String startDate = duration.getStart().format(formatter);
        String endDate = duration.getEnd().format(formatter);
        String result = startDate + ";" + endDate;
        return result;
    }
    private static Duration stringToDuration(String durationString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );

        Duration duration = new Duration();
        String[] durations = durationString.split(";");
        //TODO fehleranällig
        System.out.println("durations");
        System.out.println(durations[0]);
        LocalDate startDate = LocalDate.parse(durations[0], formatter);
        LocalDate endDate = LocalDate.parse(durations[1], formatter);
        duration.setStart(startDate);
        duration.setEnd(endDate);
        return duration;
    }

    private static RuleBasedSavingsGoalDTO createRuleBasedSavingsGoalDTO(UUID uuid, String name, String description, List<UUID> bankAccountIds, RuleBasedSavingsGoalDTO.TypeEnum typeEnum, List<Rule> rules) {
        RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = new RuleBasedSavingsGoalDTO();
        ruleBasedSavingsGoalDTO.setId(uuid);
        ruleBasedSavingsGoalDTO.setName(name);
        ruleBasedSavingsGoalDTO.setDescription(description);
        ruleBasedSavingsGoalDTO.setBankAccountIds(bankAccountIds);
        ruleBasedSavingsGoalDTO.setType(typeEnum);
        ruleBasedSavingsGoalDTO.setRules(mapModelToDTO(rules));
        return ruleBasedSavingsGoalDTO;
    }

    private static List<org.openapitools.model.Rule> mapModelToDTO(List<Rule> rules){
        List<org.openapitools.model.Rule> ruleList = new ArrayList<>();
        for(Rule rule : rules){
            org.openapitools.model.Rule tmpRule = new org.openapitools.model.Rule();
            tmpRule.setId(rule.getId());
            tmpRule.setDescription(rule.getDescription());
            tmpRule.setTarget(monetaryAmountModeltoDTO(rule.getTarget()));
            tmpRule.setBankAccountID(rule.getBankAccountID());
            ruleList.add(tmpRule);
        }
        return ruleList;
    }

    private static List<String> uuidListToStringList(List<UUID> uuidlist){
        List<String> stringList = new ArrayList<>();
        uuidlist.forEach(x->stringList.add(x.toString()));

        return stringList;
    }

    private static RuleBasedSavingsGoal createRuleBasedSavingsGoal(UUID id, String description, String name, AchievementStatus achievementStatus, List<UUID> bankAccountIds, List<Rule> rules, MatchingType matchingType){
        RuleBasedSavingsGoal ruleBasedSavingsGoal = new RuleBasedSavingsGoal();
        ruleBasedSavingsGoal.setId(id);
        ruleBasedSavingsGoal.setDescription(description);
        ruleBasedSavingsGoal.setName(name);
        ruleBasedSavingsGoal.setAchievementStatus(achievementStatus);
        ruleBasedSavingsGoal.setBankAccountIds(bankAccountIds);
        ruleBasedSavingsGoal.setRules(rules);
        ruleBasedSavingsGoal.setMatchingType(matchingType);

        return ruleBasedSavingsGoal;
    }

    private static List<Rule> convertRuleDTOToModel(List<org.openapitools.model.Rule> ruleList){
        List<Rule> newRuleList = new ArrayList<>();

        for (org.openapitools.model.Rule rule : ruleList){
            Rule newRule = new Rule();
            newRule.setBankAccountID(rule.getBankAccountID());
            newRule.setTarget(monetaryAmountDTOtoModel(rule.getTarget()));
            newRule.setDescription(rule.getDescription());
            newRule.setId(rule.getId());
        }

        return newRuleList;
    }

    private static List<SavingsRecord> convertDTOsavingsRectordToModel(List<org.openapitools.model.SavingsRecord> savingsRecord){
        if(savingsRecord==null)
            return new ArrayList<>();
        List<SavingsRecord> savingsRecordList = new ArrayList<>();
        for(org.openapitools.model.SavingsRecord sr : savingsRecord){
            SavingsRecord tmpRecord = new SavingsRecord();
            tmpRecord.setDate(getTimeFromString(sr.getDate()));
            tmpRecord.setId(sr.getId());
            tmpRecord.setAchievementStatus(sr.getAchievementStatus());
            tmpRecord.setAmount(monetaryAmountDTOtoModel(sr.getAmount()));
        }

        return savingsRecordList;
    }

    public static LocalDate getTimeFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );

        return  LocalDate.parse(date, formatter);
    }
}
