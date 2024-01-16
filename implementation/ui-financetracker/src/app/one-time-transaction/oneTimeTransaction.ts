import { MonetaryAmount } from "../bank-account/bankAccount";
import { Transfer, TransferStatus, Type } from '../transaction/transaction';

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
