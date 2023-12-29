
export class BankAccount {
    id: string;
    name: string;
    description: string;
    balance: MonetaryAmount;
    dispositionLimit: MonetaryAmount;
    labels: string[];

    constructor(id: string, name: string, description: string, balance: MonetaryAmount, dispositionLimit: MonetaryAmount, labels: string[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.balance = balance;
        this.dispositionLimit = dispositionLimit;
        this.labels = labels;
    }
}

export class MonetaryAmount {
    amount: number;

    constructor(amount: number) {
        this.amount = amount;
    }
}