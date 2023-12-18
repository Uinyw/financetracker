package com.financetracker.savingsgoal;

import java.util.UUID;

public abstract class SavingsGoal {
    private UUID id;
    private String description;
    private String name;



    public AchievmentStatus execute() {
        return AchievmentStatus.FAILED;
    }
}
