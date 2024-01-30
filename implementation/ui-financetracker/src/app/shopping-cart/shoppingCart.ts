import { Product } from "../product/product";

export class ShoppingCart {
    id: string;
    type: Type;
    productEntries: ProductEntry[];

    constructor(id: string, type: Type, productEntries: ProductEntry[]) {
        this.id = id;
        this.type = type;
        this.productEntries = productEntries;
    }
}

export enum Type {
    SHOPPING_CART = "SHOPPING_CART",
    SUPPLIES = "SUPPLIES"
}

export class ProductEntry {
    id: string;
    product: Product | null;
    quantity: number;
    desiredQuantity: number | null;
    purchased: boolean;

    constructor(id: string, product: Product, quantity: number, desiredQuantity: number, purchased: boolean) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.desiredQuantity = desiredQuantity;
        this.purchased = purchased;
    }

}
