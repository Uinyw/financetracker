package com.financetracker.savingsgoal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import com.financetracker.savingsgoal.model.SavingsGoalEntity;
import org.openapitools.api.SavingsGoalsApi;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.NativeWebRequest;


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
