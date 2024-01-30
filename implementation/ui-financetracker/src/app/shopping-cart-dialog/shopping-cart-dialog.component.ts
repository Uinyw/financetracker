import { Component, Inject } from '@angular/core';
import { ProductEntry } from '../shopping-cart/shoppingCart';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { v4 as uuidv4 } from 'uuid';
import { ProductService } from '../product/product.service';
import { Product } from '../product/product';

@Component({
  selector: 'app-shopping-cart-dialog',
  templateUrl: './shopping-cart-dialog.component.html',
  styleUrls: ['./shopping-cart-dialog.component.scss']
})
export class ShoppingCartDialogComponent {

  editMode: boolean
  productEntry: ProductEntry

  products: Product[] = []

  constructor(@Inject(MAT_DIALOG_DATA) private data: ProductEntry,
              private dialogRef: MatDialogRef<ShoppingCartDialogComponent>,
              private productService: ProductService) {

    productService.getAllProducts().subscribe(result => this.products = result)

    this.editMode = data != null

    if (this.editMode) {
      this.productEntry = data;
    } else {
      this.productEntry = {
        id: uuidv4(),
        product: null,
        quantity: 0,
        desiredQuantity: null,
        purchased: false
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingProductEntry() {
    return this.productEntry;
  }
  
  public compareProductEntry = function( option: ProductEntry, value: ProductEntry ) : boolean {
    return option.id === value.id;
  }


}
