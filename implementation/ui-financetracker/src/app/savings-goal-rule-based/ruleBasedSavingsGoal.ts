import { MonetaryAmount } from "../product/product";

export class RuleBasedSavingsGoal {
    id: string;
    name: string;
    description: string;
    achievementStatus: AchievementStatus;
    type: Type;
    rules: Rule[];

    constructor(id: string, name: string, description: string, achievementStatus: AchievementStatus, type: Type, rules: Rule[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.achievementStatus = achievementStatus;
        this.type = type;
        this.rules = rules;
    }
}

export enum AchievementStatus {
    IN_PROGRESS = "IN_PROGRESS",
    ACHIEVED = "ACHIEVED",
    FAILED = "FAILED"
}

export enum Type {
    MATCH_ALL = "MATCH_ALL",
    MATCH_ANY = "MATCH_ANY",
}

export class Rule {
    id: string;
    description: string;
    ruleType: RuleType;
    bankAccountID: string;
    target: MonetaryAmount;

    constructor(id: string, description: string, ruleType: RuleType, bankAccountID: string, target: MonetaryAmount) {
        this.id = id;
        this.description = description;
        this.ruleType = ruleType;
        this.bankAccountID = bankAccountID;
        this.target = target;
    }
}

export enum RuleType {
    EQUALS = "EQUALS",
    GREATER_THAN = "GREATER_THAN",
    LESS_THAN = "LESS_THAN"
}
