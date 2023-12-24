package com.financetracker.savingsgoal;

import java.util.List;
import java.util.UUID;

public class Rule {
    private UUID id;
    private List<UUID> bankAccountID; //changed this to iterate over it later on
    private String description;
    private MonetaryAmount target;
    private RuleType ruleType;

    public Rule(List<UUID> bankAccountID, String description, MonetaryAmount target, RuleType ruleType) {
        this.id = UUID.randomUUID();
        this.bankAccountID = bankAccountID;
        this.description = description;
        this.target = target;
        this.ruleType = ruleType;
    }

    public Rule(List<UUID> bankAccountID, MonetaryAmount target, RuleType ruleType) {
        this.id = UUID.randomUUID();
        this.bankAccountID = bankAccountID;
        this.target = target;
    }


    /***
     * This method applies the rule to the bank accounts
     * @return
     */
    public Boolean apply() {
        switch (ruleType) {
            case EQUALS:
                return checkEqualAmpunt();
            case GREATER_THAN:
                return checkBiggerAmount();
            case LESS_THAN:
                return checkSmallerAmount();
        }
        return null;
    }

    /**
     * This method checks if the amount of the bank accounts is equal to the target amount
     * @return true if the amount is equal to the target amount
     */
    private boolean checkEqualAmpunt(){
        Boolean returnValue = true;
        for (UUID uuid : bankAccountID) {
            if (getMoney(uuid) != target.getAmount()) {
                returnValue = false;
            }
        }
        return returnValue;
    }

    /**
     * This method checks if the amount of the bank accounts is bigger than the target amount
     * @return true if the amount is bigger than the target amount
     */
    private boolean checkBiggerAmount(){
        Boolean returnValue = true;
        for (UUID uuid : bankAccountID) {
            if (getMoney(uuid) < target.getAmount()) {
                returnValue = false;
            }
        }
        return returnValue;
    }

    /**
     * This method checks if the amount of the bank accounts is smaller than the target amount
     * @return true if the amount is smaller than the target amount
     */
    private boolean checkSmallerAmount(){
        Boolean returnValue = true;
        for (UUID uuid : bankAccountID) {
            if (getMoney(uuid) > target.getAmount()) {
                returnValue = false;
            }
        }
        return returnValue;
    }

    /**
     * This method returns the amount of the bank account TODO this method needs to be altered
     * @param id 
     * @return the money within the bank account
     */
    private double getMoney(UUID bankAccountId){
        //TODO delete this class later on
        return 0;
    }
}
