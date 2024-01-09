package com.financetracker.savingsgoal;

import java.util.List;

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
        System.out.println("get the periodical savings goals");
        return new ResponseEntity<>(savingsGoalService.getPeriodicalSavingsGoals(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalPost(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        System.out.println("post a periodical savings goal");
        savingsGoalService.createPeriodicalSavingsGoal(periodicalSavingsGoalDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalidDelete(String id) {
        System.out.println("delete a periodical savings goal");
        boolean worked = savingsGoalService.deletePeriodicalSavingsGoal(id);
        return worked ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PeriodicalSavingsGoalDTO> savingsGoalsPeriodicalidGet(String id) {
        return new ResponseEntity<>(savingsGoalService.getPeriodicalSavingsGoal(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalidPatch(String id, PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        //TODO patch
        return SavingsGoalsApi.super.savingsGoalsPeriodicalidPatch(id, periodicalSavingsGoalDTO);
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
        //TODO patch
        return SavingsGoalsApi.super.savingsGoalsRuleBasedIdPatch(id, ruleBasedSavingsGoalDTO);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsRuleBasedPost(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO) {
        return SavingsGoalsApi.super.savingsGoalsRuleBasedPost(ruleBasedSavingsGoalDTO);
    }

}
