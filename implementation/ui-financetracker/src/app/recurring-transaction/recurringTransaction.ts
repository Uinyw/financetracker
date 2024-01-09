import { MonetaryAmount } from "../bank-account/bankAccount";
import { Periodicity, Transfer, TransferStatus, Type } from "../transaction/transaction";

export class RecurringTransaction {
    id: string;
    name: string;
    description: string;
    type: Type;
    labels: string[];
    transfer: Transfer;
    startDate: string;
    periodicity: Periodicity;
    fixedAmount: MonetaryAmount;
    transactionRecords: TransactionRecord[];

    constructor(id: string, name: string, description: string, type: Type, labels: string[], transfer: Transfer, startDate: string, periodicity: Periodicity, fixedAmount: MonetaryAmount, transactionRecords: TransactionRecord[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.labels = labels;
        this.transfer = transfer;
        this.startDate = startDate;
        this.periodicity = periodicity;
        this.fixedAmount = fixedAmount;
        this.transactionRecords = transactionRecords;
    }
}

export class TransactionRecord {
    id: string;
    date: string;
    amount: MonetaryAmount;
    transferStatus: TransferStatus;

    constructor(id: string, date: string, amount: MonetaryAmount, transferStatus: TransferStatus) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.transferStatus = transferStatus;
    }

}
