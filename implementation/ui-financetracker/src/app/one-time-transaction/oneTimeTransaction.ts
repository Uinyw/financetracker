import { MonetaryAmount } from "../bank-account/bankAccount";

export class OneTimeTransaction {
    id: string;
    name: string;
    description: string;
    type: Type;
    labels: string[];
    transfer: Transfer;
    amount: MonetaryAmount;
    date: string;
    transferStatus: TransferStatus;

    constructor(id: string, name: string, description: string, type: Type, labels: string[], transfer: Transfer, amount: MonetaryAmount, date: string, transferStatus: TransferStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.labels = labels;
        this.transfer = transfer;
        this.amount = amount;
        this.date = date;
        this.transferStatus = transferStatus;
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
    EXPENSE = "EXPENSE"
}