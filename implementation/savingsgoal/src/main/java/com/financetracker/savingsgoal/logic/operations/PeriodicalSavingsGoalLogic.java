package com.financetracker.savingsgoal.logic.operations;

import com.financetracker.savingsgoal.logic.model.PeriodicalSavingsGoal;
import com.financetracker.savingsgoal.infrastructure.db.PeriodicalSavingsGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PeriodicalSavingsGoalLogic {

    private final PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;

    public List<PeriodicalSavingsGoal> getPeriodicalSavingsGoals() {
        return periodicalSavingsGoalRepository.findAll();
    }

    public void createPeriodicalSavingsGoal(PeriodicalSavingsGoal periodicalSavingsGoal) {
        periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
    }

    public void updatePeriodicalSavingsGoal(final String savingsGoalId, final PeriodicalSavingsGoal periodicalSavingsGoal) {
        final var originalSavingsGoal = findPeriodicalSavingsGoalById(savingsGoalId);
        if (originalSavingsGoal == null) {
            return;
        }

        periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
    }

    public boolean deletePeriodicalSavingsGoal(String id) {
        PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
        if (psg == null)
            return false;

        periodicalSavingsGoalRepository.delete(psg);
        return true;
    }

    public PeriodicalSavingsGoal findPeriodicalSavingsGoalById(String id) {
        for (PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()) {
            if (sg.getId().toString().equals(id)) {
                return sg;
            }
        }
        return null;
    }
}
