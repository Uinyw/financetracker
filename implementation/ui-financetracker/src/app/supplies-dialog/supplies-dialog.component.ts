import { Component, Inject } from '@angular/core';
import { ProductEntry } from '../shopping-cart/shoppingCart';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Product } from '../product/product';
import { ProductService } from '../product/product.service';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-supplies-dialog',
  templateUrl: './supplies-dialog.component.html',
  styleUrls: ['./supplies-dialog.component.scss']
})
export class SuppliesDialogComponent {

  editMode: boolean
  productEntry: ProductEntry

  products: Product[] = []

  constructor(@Inject(MAT_DIALOG_DATA) private data: ProductEntry,
              private dialogRef: MatDialogRef<SuppliesDialogComponent>,
              private productService: ProductService) {

    productService.getAllProducts().subscribe(result => this.products = result)

    this.editMode = data != null

    if (this.editMode) {
      this.productEntry = data;
      console.log(this.productEntry)
    } else {
      this.productEntry = {
        id: uuidv4(),
        product: null,
        quantity: 0,
        desiredQuantity: 0,
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
