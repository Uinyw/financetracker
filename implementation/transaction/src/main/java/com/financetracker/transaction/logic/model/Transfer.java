package com.financetracker.transaction.logic.model;

public record Transfer(String sourceBankAccountId, String externalSourceId, String targetBankAccountId) {

}
