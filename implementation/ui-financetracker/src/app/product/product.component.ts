import { Component, ViewChild } from '@angular/core';
import { Product } from './product';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { ProductService } from './product.service';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { ProductDialogComponent } from '../product-dialog/product-dialog.component';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent {

  displayedColumns: string[] = ['name', 'description', 'price', 'category', 'size', 'carbs', 'protein', 'fat', 'sugar', 'labels', 'actions'];
  products = new MatTableDataSource<Product>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private productService: ProductService, private dialog: MatDialog) {

  }

  ngOnInit() {
    this.products.paginator = this.paginator;
    this.productService.getAllProducts().subscribe(products => this.products.data = products.sort((a, b) => a.name.localeCompare(b.name)));
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.products.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(ProductDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.productService.createProduct(result).subscribe(() => {
        this.productService.getAllProducts().subscribe(products => this.products.data = products.sort((a, b) => a.name.localeCompare(b.name)));
      })
    });
  }

  openEditDialog(product: Product): void {
    const dialogRef = this.dialog.open(ProductDialogComponent, { width: '60%', data: product });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.productService.editProduct(result).subscribe(() => {
        this.productService.getAllProducts().subscribe(products => this.products.data = products.sort((a, b) => a.name.localeCompare(b.name)));
      })
    });
  }

  deleteProduct(id: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.value) {
        this.productService.deleteProduct(id).subscribe(() => {
          this.products.data = this.products.data.filter(product => product.id != id);
        })
      }
    });
  }

}
