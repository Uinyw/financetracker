package com.financetracker.transaction.logic.model;

import java.time.LocalDate;

public interface Transferable {

    LocalDate getDate();
    MonetaryAmount getAmount();
    TransferStatus getTransferStatus();
    void setTransferStatus(TransferStatus transferStatus);

}
