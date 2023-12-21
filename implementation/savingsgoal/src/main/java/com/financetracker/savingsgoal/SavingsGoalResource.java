package com.financetracker.savingsgoal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.openapitools.api.SavingsGoalsApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.model.SavingsGoal;
import org.openapitools.model.SavingsGoalsGet200ResponseInner;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class SavingsGoalResource implements SavingsGoalsApi{

    private final SavingsGoalService savingsGoalService;

    @Override
    public ResponseEntity<List<SavingsGoalsGet200ResponseInner>> savingsGoalsGet() {
        
        System.out.println("savings goal created");

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Void> savingsGoalsPost(@Valid @RequestBody SavingsGoal savingsGoal) {
        System.out.println("savings goal saved");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> savingsGoalsIdDelete(@PathVariable("id") String id) {
        
        System.out.println("deleted savigns goal");

        return true ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SavingsGoalsGet200ResponseInner> savingsGoalsIdGet(@PathVariable("id") String id) {
        System.out.println("searching for a savings goal");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
