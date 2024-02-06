import { MonetaryAmount } from "../product/product";

export class RuleBasedSavingsGoal {
    id: string;
    name: string;
    description: string;
    achievementStatus: AchievementStatus;
    matchingType: MatchingType;
    rules: Rule[];

    constructor(id: string, name: string, description: string, achievementStatus: AchievementStatus, matchingType: MatchingType, rules: Rule[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.achievementStatus = achievementStatus;
        this.matchingType = matchingType;
        this.rules = rules;
    }
}

export enum AchievementStatus {
    IN_PROGRESS = "IN_PROGRESS",
    ACHIEVED = "ACHIEVED",
    FAILED = "FAILED"
}

export enum MatchingType {
    MATCH_ALL = "MATCH_ALL",
    MATCH_ANY = "MATCH_ANY",
}

export class Rule {
    id: string;
    description: string;
    type: RuleType;
    bankAccountId: string;
    target: MonetaryAmount;

    constructor(id: string, description: string, type: RuleType, bankAccountId: string, target: MonetaryAmount) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.target = target;
    }
}

export enum RuleType {
    EQUALS = "EQUALS",
    GREATER_THAN = "GREATER_THAN",
    LESS_THAN = "LESS_THAN"
}
