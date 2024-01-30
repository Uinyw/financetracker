package com.financetracker.savingsgoal.api;

import java.util.List;

import com.financetracker.savingsgoal.logic.operations.SavingsGoalService;
import org.apache.commons.lang3.NotImplementedException;
import org.openapitools.api.SavingsGoalsApi;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class SavingsGoalResource implements SavingsGoalsApi {

    private final SavingsGoalService savingsGoalService;

    @Override
    public ResponseEntity<List<PeriodicalSavingsGoalDTO>> savingsGoalsPeriodicalGet() {
        return new ResponseEntity<>(savingsGoalService.getPeriodicalSavingsGoals(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalPost(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        savingsGoalService.createPeriodicalSavingsGoal(periodicalSavingsGoalDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalidDelete(String id) {
        boolean worked = savingsGoalService.deletePeriodicalSavingsGoal(id);
        return worked ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PeriodicalSavingsGoalDTO> savingsGoalsPeriodicalidGet(String id) {
        return new ResponseEntity<>(savingsGoalService.getPeriodicalSavingsGoal(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalidPatch(String id, PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<List<RuleBasedSavingsGoalDTO>> savingsGoalsRuleBasedGet() {
        return new ResponseEntity<>(savingsGoalService.getRuleBasedSavingsGoals(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsRuleBasedIdDelete(String id) {
        boolean worked = savingsGoalService.deleteRuleBasedSavingsGoal(id);
        return worked ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RuleBasedSavingsGoalDTO> savingsGoalsRuleBasedIdGet(String id) {
        return new ResponseEntity<>(savingsGoalService.getRuleBasedSavingsGoal(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsRuleBasedIdPatch(String id, RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<Void> savingsGoalsRuleBasedPost(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO) {
        savingsGoalService.createRuleBasedSavingsGoal(ruleBasedSavingsGoalDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
