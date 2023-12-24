package com.financetracker.savingsgoal;

import java.sql.Date;
import java.util.UUID;

public class SavingsRecord {
    private UUID id;
    private Date date;
    private MonetaryAmount amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
