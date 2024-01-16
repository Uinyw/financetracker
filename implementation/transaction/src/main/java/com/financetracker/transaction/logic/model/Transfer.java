package com.financetracker.transaction.logic.model;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Transfer {

    private String sourceBankAccountId;
    private String externalSourceId;
    private String targetBankAccountId;
    private String externalTargetId;

    public Transfer(final String sourceBankAccountId,
                    final String externalSourceId,
                    final String targetBankAccountId,
                    final String externalTargetId) {

        this.sourceBankAccountId = sourceBankAccountId;
        this.externalSourceId = externalSourceId;
        this.targetBankAccountId = targetBankAccountId;
        this.externalTargetId = externalTargetId;

        if (!isShift() && !isIncome() && !isExpense()) {
            throw new NotParseableException();
        }
    }

    public boolean isShift() {
        return sourceBankAccountId != null && externalSourceId == null && targetBankAccountId != null && externalTargetId == null;
    }

    public boolean isIncome() {
        return sourceBankAccountId == null && externalSourceId != null && targetBankAccountId != null && externalTargetId == null;
    }

    public boolean isExpense() {
        return sourceBankAccountId != null && externalSourceId == null && targetBankAccountId == null && externalTargetId != null;
    }

    public boolean hasInternalSource() {
        return isShift() || isExpense();
    }

    public boolean hasInternalTarget() {
        return isShift() || isIncome();
    }

    public Transfer invert() {
        if (!isShift()) {
            return null;
        }
        return new Transfer(targetBankAccountId, externalSourceId, sourceBankAccountId, externalTargetId);
    }

}
