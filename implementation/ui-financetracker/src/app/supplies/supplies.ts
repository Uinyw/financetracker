import { ProductEntry, Type } from "../shopping-cart/shoppingCart";

export class Supplies {
    id: string;
    type: Type;
    productEntries: ProductEntry[];

    constructor(id: string, type: Type, productEntries: ProductEntry[]) {
        this.id = id;
        this.type = type;
        this.productEntries = productEntries;
    }
}
