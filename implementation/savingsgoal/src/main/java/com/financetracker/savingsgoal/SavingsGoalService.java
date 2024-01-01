package com.financetracker.savingsgoal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.stereotype.Service;
import com.financetracker.savingsgoal.model.SavingsGoalEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingsGoalService {

    private final SavingsGoalFactory savingsGoalFactory;
    private final SavingsGoalRepository savingsGoalRepository;

     public List<SavingsGoalEntity> get() {
        List<SavingsGoalEntity> savingsGoalEntities = savingsGoalRepository.findAll();
        return savingsGoalEntities;
    }

    public SavingsGoal getSavingsGoalById(UUID id) {
        Optional<SavingsGoalEntity> savingsGoal = get().stream().filter(ba -> ba.getId().equals(id)).findFirst();
        return null;
    }

    public List<SavingsGoalEntity> getSavingsGoals() {
         List<SavingsGoal> bankAccountEntities;
        return null;
    }

    public Boolean deleteSavingsGoalById(String id) {
        return false;
    }



 public List<PeriodicalSavingsGoalDTO> getPeriodicalSavingsGoals(){
    //TODO implement
     return null;
 }
 public boolean createPeriodicalSavingsGoal(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO){
     //TODO implement
     return false;
 }

public boolean deletePeriodicalSavingsGoal(String id){
         //TODO implement
         return false;
}

public PeriodicalSavingsGoalDTO getPeriodicalSavingsGoal(String id){
    //TODO implement
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
