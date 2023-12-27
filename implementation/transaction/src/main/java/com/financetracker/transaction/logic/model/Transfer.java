package com.financetracker.transaction.logic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    private String sourceBankAccountId;
    private String externalSourceId;
    private String targetBankAccountId;

    public boolean isInternalTransfer() {
        return sourceBankAccountId != null;
    }

    public Transfer invert() {
        if (!isInternalTransfer()) {
            return null;
        }
        return new Transfer(targetBankAccountId, externalSourceId, sourceBankAccountId);
    }

}
