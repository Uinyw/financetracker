package com.financetracker.savingsgoal.logic.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Duration {
    private LocalDate start;
    private LocalDate end;
}
