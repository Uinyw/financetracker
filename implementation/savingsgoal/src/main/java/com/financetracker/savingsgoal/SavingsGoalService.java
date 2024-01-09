package com.financetracker.savingsgoal;

import java.util.ArrayList;
import java.util.List;

import com.financetracker.savingsgoal.model.SavingsGoalMapper;
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
        //TODO implement
        return null;
    }
    public boolean createRuleBasedSavingsGoal(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO){
        //TODO implement
        return false;
    }

    public boolean deleteRuleBasedSavingsGoal(String id){
        //TODO implement
        return false;
    }

    public RuleBasedSavingsGoalDTO getRuleBasedSavingsGoal(String id){
        //TODO implement
        return null;
    }

}
