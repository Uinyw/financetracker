package com.financetracker.savingsgoal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.financetracker.savingsgoal.model.SavingsGoalMapper;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingsGoalService {

    private final SavingsGoalFactory savingsGoalFactory;
    private final PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;
    private final RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository;
    private final SavingsGoalMapper savingsGoalMapper;

 public List<PeriodicalSavingsGoalDTO> getPeriodicalSavingsGoals(){
     List<PeriodicalSavingsGoalDTO> periodicalSavingsGoalDTOList = new ArrayList<>();
     for(PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()){
         periodicalSavingsGoalDTOList.add(savingsGoalMapper.periodicalSavingsGoalEntityToDTO(sg));
     }
     return periodicalSavingsGoalDTOList;
 }
 public boolean createPeriodicalSavingsGoal(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO){
     PeriodicalSavingsGoal periodicalSavingsGoal = savingsGoalMapper.periodicalSavingsGoalDTOtoEntity(periodicalSavingsGoalDTO);
     periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
     return true;
 }

public boolean deletePeriodicalSavingsGoal(String id){
    PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
    if(psg == null)
        return false;
    periodicalSavingsGoalRepository.delete(psg);
    return true;
}

public PeriodicalSavingsGoalDTO getPeriodicalSavingsGoal(String id){
    PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
    return savingsGoalMapper.periodicalSavingsGoalEntityToDTO(psg);
}

private PeriodicalSavingsGoal findPeriodicalSavingsGoalById(String id){
    for(PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()){
        if(sg.getId().toString().equals(id)){
            return sg;
        }
    }
    return null;
}

//-----------

    public List<RuleBasedSavingsGoalDTO> getRuleBasedSavingsGoals(){
        System.out.println("getting all the rule based savings goals");
        List<RuleBasedSavingsGoalDTO> ruleBasedSavingsGoalDTOS = new ArrayList<>();
        ruleBasedSavingsGoalRepository.findAll().forEach(rbsg
                -> ruleBasedSavingsGoalDTOS.add(savingsGoalMapper.ruleBasedSavingsGoalEntityToDTO(rbsg)));
        return ruleBasedSavingsGoalDTOS;
    }
    public boolean createRuleBasedSavingsGoal(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO){
        ruleBasedSavingsGoalRepository.save(savingsGoalMapper.ruleBasedSavingsGoalDTOtoEntity(ruleBasedSavingsGoalDTO));
        System.out.println("rule based savings goal has been created");
        return true;
    }

    public boolean deleteRuleBasedSavingsGoal(String id){
        RuleBasedSavingsGoal ruleBasedSavingsGoal = findRuleBasedSavingsGoalById(id);
        if(ruleBasedSavingsGoal == null)
            return false;

        ruleBasedSavingsGoalRepository.delete(ruleBasedSavingsGoal);
        return true;
    }

    public RuleBasedSavingsGoalDTO getRuleBasedSavingsGoal(String id){
        RuleBasedSavingsGoal ruleBasedSavingsGoal = findRuleBasedSavingsGoalById(id);
        return savingsGoalMapper.ruleBasedSavingsGoalEntityToDTO(ruleBasedSavingsGoal);
    }

    private RuleBasedSavingsGoal findRuleBasedSavingsGoalById(String id){
        for(RuleBasedSavingsGoal sg : ruleBasedSavingsGoalRepository.findAll()){
            if(sg.getId().toString().equals(id)){
                return sg;
            }
        }
        return null;
    }

    public PeriodicalSavingsGoalDTO createRandomPeriodicalSavingsGoal(){
        com.financetracker.savingsgoal.MonetaryAmount newMonetaryAmount = new com.financetracker.savingsgoal.MonetaryAmount();
        newMonetaryAmount.setAmount(Math.random()*10000);
        Duration duration = new Duration();
        duration.setStart(LocalDate.now());
        duration.setEnd(LocalDate.of(2025,10,5));

     PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO = new PeriodicalSavingsGoalDTO();

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
        periodicalSavingsGoalDTO.setSavingsRecord(new ArrayList<>());
        return periodicalSavingsGoalDTO;
    }
    public RuleBasedSavingsGoalDTO createRandomRBasedSavingsGoal(){
        RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = new RuleBasedSavingsGoalDTO();
        ruleBasedSavingsGoalDTO.setId(UUID.randomUUID());
        ruleBasedSavingsGoalDTO.setName("name");
        ruleBasedSavingsGoalDTO.setDescription("description");
        ruleBasedSavingsGoalDTO.setBankAccountIds(new ArrayList<>());
        ruleBasedSavingsGoalDTO.setType(RuleBasedSavingsGoalDTO.TypeEnum.ANY);
        ruleBasedSavingsGoalDTO.setRules(new ArrayList<>());
        return ruleBasedSavingsGoalDTO;
    }
}
