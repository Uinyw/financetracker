package com.financetracker.transaction.logic.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Getter
@RequiredArgsConstructor
public class TransactionRecord implements Transferable {

    private final LocalDate date;
    private final MonetaryAmount amount;
}
