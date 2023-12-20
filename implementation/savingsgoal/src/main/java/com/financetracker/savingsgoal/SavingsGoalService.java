package com.financetracker.savingsgoal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.financetracker.savingsgoal.model.SavingsGoalEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingsGoalService {

    private final SavingsGoalFactory savingsGoalFactory;
    private final SavingsGoalRepository savingsGoalRepository;

     public List<SavingsGoalEntity> get() {
        List<SavingsGoalEntity> sgentities = savingsGoalRepository.findAll();
        return null;
    }

    public SavingsGoal getSavingsGoalById(UUID id) {
        Optional<SavingsGoalEntity> savingsGoal = get().stream().filter(ba -> ba.getId().equals(id)).findFirst();
        return null;
    }

    public List<SavingsGoal> getSavingsGoals() {
         List<SavingsGoal> bankAccountEntities;
        return null;
    }

    public Boolean deleteSavingsGoalById(String id) {
        return false;
    }
}
