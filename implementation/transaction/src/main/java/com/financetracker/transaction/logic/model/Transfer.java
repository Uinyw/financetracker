package com.financetracker.transaction.logic.model;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import lombok.AllArgsConstructor;
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
        if (sourceBankAccountId == null && targetBankAccountId == null) {
            throw new NotParseableException();
        }

        if ((sourceBankAccountId == null && externalSourceId == null) || (targetBankAccountId == null && externalTargetId == null)) {
            throw new NotParseableException();
        }

        if (sourceBankAccountId != null && externalSourceId != null) {
            throw new NotParseableException();
        }

        if (targetBankAccountId != null && externalTargetId != null) {
            throw new NotParseableException();
        }

        this.sourceBankAccountId = sourceBankAccountId;
        this.externalSourceId = externalSourceId;
        this.targetBankAccountId = targetBankAccountId;
        this.externalTargetId = externalTargetId;
    }

    public boolean hasInternalSource() {
        return sourceBankAccountId != null;
    }

    public boolean hasInternalTarget() {
        return targetBankAccountId != null;
    }

    public Transfer invert() {
        if (!hasInternalSource() || !hasInternalTarget()) {
            return null;
        }
        return new Transfer(targetBankAccountId, externalSourceId, sourceBankAccountId, externalTargetId);
    }

}
