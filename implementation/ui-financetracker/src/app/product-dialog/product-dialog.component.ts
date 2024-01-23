import { Component, Inject } from '@angular/core';
import { Category, Product } from '../product/product';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-product-dialog',
  templateUrl: './product-dialog.component.html',
  styleUrls: ['./product-dialog.component.scss']
})
export class ProductDialogComponent {

  editMode: boolean
  product: Product
  labels: string = ""

  categories = Object.values(Category);

  constructor(@Inject(MAT_DIALOG_DATA) private data: Product, private dialogRef: MatDialogRef<ProductDialogComponent>) {
    this.editMode = data != null

    if (this.editMode) {
      this.product = data;
      this.labels = data != null ? data.labels.join(",") : "";
    } else {
      this.product = {
        id: uuidv4(),
        name: "",
        description: "",
        price: {
          amount: 0
        },
        nutrition: null,
        category: Category.FOOD,
        labels: []
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingProduct() {
    this.product.labels = this.labels != null ? this.labels.split(",") : []
    return this.product;
  }

}
