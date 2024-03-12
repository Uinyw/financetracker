
export class Product {
    id: string;
    name: string;
    description: string;
    price: MonetaryAmount;
    category: Category;
    size: number;
    nutrition: Nutrition | null;
    labels: string[];

    constructor(id: string, name: string, description: string, price: MonetaryAmount, category: Category, size: number, nutrition: Nutrition, labels: string[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.size = size;
        this.nutrition = nutrition;
        this.labels = labels;
    }
}

export class MonetaryAmount {
    amount: number | null;

    constructor(amount: number) {
        this.amount = amount;
    }
}

export class Nutrition {
    servingSize: number;
    calories: number;
    carbohydrates: number;
    protein: number;
    fat: number;
    sugar: number;

    constructor(servingSize: number, calories: number, carbohydrates: number, protein: number, fat: number, sugar: number) {
        this.servingSize = servingSize;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;
        this.sugar = sugar;
    }
}

export enum Category {
    FOOD = "FOOD",
    DRINKS = "DRINKS",
    HOUSEHOLD = "HOUSEHOLD",
    ESSENTIALS = "ESSENTIALS",
    PET_SUPPLIES = "PET_SUPPLIES"
}
