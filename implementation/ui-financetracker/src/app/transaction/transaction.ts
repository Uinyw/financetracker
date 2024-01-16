import { MonetaryAmount } from "../bank-account/bankAccount";

export class Transaction {
    name: string;
    date: string;
    transfer: Transfer;
    amount: MonetaryAmount;
    plus: boolean;

    constructor(name: string, date: string, transfer: Transfer, amount: MonetaryAmount, plus: boolean) {
        this.name = name;
        this.date = date;
        this.transfer = transfer;
        this.amount = amount;
        this.plus = plus;
    }
}


export class Transfer {
    sourceBankAccountId: string | null;
    externalSourceId: string | null;
    targetBankAccountId: string | null;
    externalTargetId: string | null;

    constructor(sourceBankAccountId: string, externalSourceId: string, targetBankAccountId: string, externalTargetId: string) {
        this.sourceBankAccountId = sourceBankAccountId;
        this.externalSourceId = externalSourceId;
        this.targetBankAccountId = targetBankAccountId;
        this.externalTargetId = externalTargetId;
    }
}

export enum TransferStatus {
    INITIAL = "INITIAL",
    FAILED = "FAILED",
    SUCCESSFUL = "SUCCESSFUL"
}

export enum Type {
    INCOME = "INCOME",
    EXPENSE = "EXPENSE",
    SHIFT = "SHIFT"
}

export enum Periodicity {
    MONTHLY = "MONTHLY",
    QUARTERLY = "QUARTERLY",
    HALF_YEARLY = "HALF_YEARLY",
    YEARLY = "YEARLY"
}