package com.financetracker.transaction.logic.model;

public record Transfer(String sourceBankAccountId, String externalSourceId, String targetBankAccountId) {

    public boolean isInternalTransfer() {
        return sourceBankAccountId() != null;
    }

    public Transfer invert() {
        if (!isInternalTransfer()) {
            return null;
        }
        return new Transfer(targetBankAccountId, externalSourceId, sourceBankAccountId);
    }

}
