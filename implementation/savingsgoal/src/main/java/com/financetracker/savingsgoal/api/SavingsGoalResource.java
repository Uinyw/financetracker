package com.financetracker.savingsgoal.api;

import java.util.List;

import com.financetracker.savingsgoal.api.mapping.PeriodicalSavingsGoalMapper;
import com.financetracker.savingsgoal.api.mapping.RuleBasedSavingsGoalMapper;
import com.financetracker.savingsgoal.logic.operations.SavingsGoalService;
import org.openapitools.api.SavingsGoalsApi;
import org.openapitools.model.PeriodicalSavingsGoalDto;
import org.openapitools.model.RuleBasedSavingsGoalDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class SavingsGoalResource implements SavingsGoalsApi {

    private final PeriodicalSavingsGoalMapper periodicalSavingsGoalMapper;
    private final RuleBasedSavingsGoalMapper ruleBasedSavingsGoalMapper;
    private final SavingsGoalService savingsGoalService;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<List<PeriodicalSavingsGoalDto>> savingsGoalsPeriodicalGet() {
        final var result = savingsGoalService.getPeriodicalSavingsGoals().stream().map(periodicalSavingsGoalMapper::periodicalSavingsGoalEntityToDto).toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<PeriodicalSavingsGoalDto> savingsGoalsPeriodicalIdGet(String id) {
        return new ResponseEntity<>(periodicalSavingsGoalMapper.periodicalSavingsGoalEntityToDto(savingsGoalService.getPeriodicalSavingsGoal(id)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalPost(PeriodicalSavingsGoalDto periodicalSavingsGoalDto) {
        savingsGoalService.createPeriodicalSavingsGoal(periodicalSavingsGoalMapper.periodicalSavingsGoalDtoToEntity(periodicalSavingsGoalDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalIdDelete(String id) {
        boolean worked = savingsGoalService.deletePeriodicalSavingsGoal(id);
        return worked ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> savingsGoalsPeriodicalIdPatch(String id, PeriodicalSavingsGoalDto periodicalSavingsGoalDto) {
        savingsGoalService.updatePeriodicalSavingsGoal(id, periodicalSavingsGoalMapper.periodicalSavingsGoalDtoToEntity(periodicalSavingsGoalDto));
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<List<RuleBasedSavingsGoalDto>> savingsGoalsRuleBasedGet() {
        final var result = savingsGoalService.getRuleBasedSavingsGoals().stream().map(ruleBasedSavingsGoalMapper::ruleBasedSavingsGoalEntityToDto).toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<RuleBasedSavingsGoalDto> savingsGoalsRuleBasedIdGet(String id) {
        return new ResponseEntity<>(ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalEntityToDto(savingsGoalService.getRuleBasedSavingsGoal(id)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> savingsGoalsRuleBasedPost(RuleBasedSavingsGoalDto ruleBasedSavingsGoalDto) {
        savingsGoalService.createRuleBasedSavingsGoal(ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalDtoToEntity(ruleBasedSavingsGoalDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> savingsGoalsRuleBasedIdDelete(String id) {
        boolean worked = savingsGoalService.deleteRuleBasedSavingsGoal(id);
        return worked ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> savingsGoalsRuleBasedIdPatch(String id, RuleBasedSavingsGoalDto ruleBasedSavingsGoalDto) {
        boolean worked = savingsGoalService.updateRuleBasedSavingsGoal(id, ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalDtoToEntity(ruleBasedSavingsGoalDto));
        return worked ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }



}
