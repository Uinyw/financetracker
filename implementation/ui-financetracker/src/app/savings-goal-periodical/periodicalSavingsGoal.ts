import { MonetaryAmount } from "../product/product";
import { AchievementStatus } from "../savings-goal-rule-based/ruleBasedSavingsGoal";
import { Periodicity } from "../transaction/transaction";

export class PeriodicalSavingsGoal {
    id: string;
    name: string;
    description: string;
    achievementStatus: AchievementStatus;
    sourceBankAccountId: string;
    targetBankAccountId: string;
    goal: MonetaryAmount;
    recurringRate: number | null;
    recurringAmount: MonetaryAmount;
    duration: string;
    periodicity: Periodicity;
    savingsRecords: SavingsRecord[]


    constructor(id: string, name: string,
                description: string,
                achievementStatus: AchievementStatus,
                sourceBankAccountId: string,
                targetBankAccountId: string,
                goal: MonetaryAmount,
                recurringRate: number,
                recurringAmount: MonetaryAmount,
                duration: string,
                periodicity: Periodicity,
                savingsRecords: SavingsRecord[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.achievementStatus = achievementStatus;
        this.sourceBankAccountId = sourceBankAccountId;
        this.targetBankAccountId = targetBankAccountId;
        this.goal = goal;
        this.recurringRate = recurringRate;
        this.recurringAmount = recurringAmount;
        this.duration = duration;
        this.periodicity = periodicity;
        this.savingsRecords = savingsRecords;
    }
}

export class SavingsRecord {
    id: string;
    date: string;
    amount: MonetaryAmount
    achievementStatus: AchievementStatus;

    constructor(id: string, date: string, amount: MonetaryAmount, achievementStatus: AchievementStatus) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.achievementStatus = achievementStatus;
    }
}
