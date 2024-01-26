import { Component, ViewChild } from '@angular/core';
import { ProductEntry } from '../shopping-cart/shoppingCart';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { SuppliesService } from './supplies.service';
import { SuppliesDialogComponent } from '../supplies-dialog/supplies-dialog.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-supplies',
  templateUrl: './supplies.component.html',
  styleUrls: ['./supplies.component.scss']
})
export class SuppliesComponent {

  displayedColumns: string[] = ['name', 'quantity', 'desiredQuantity', 'price', 'actions'];
  productEntries = new MatTableDataSource<ProductEntry>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private suppliesService: SuppliesService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.productEntries.paginator = this.paginator;
    this.suppliesService.getSupplies().subscribe(supplies => this.productEntries.data = supplies.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
  }

  addRequiredSuppliesToShoppingCart() {
    this.suppliesService.addRequiredProductsToShoppingCart().subscribe(result => {
      this.suppliesService.getSupplies().subscribe(supplies => this.productEntries.data = supplies.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
    })
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.productEntries.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(SuppliesDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.suppliesService.createProductEntry(result).subscribe(() => {
        this.suppliesService.getSupplies().subscribe(supplies => this.productEntries.data = supplies.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
      })
    });
  }

  openEditDialog(productEntry: ProductEntry): void {
    const dialogRef = this.dialog.open(SuppliesDialogComponent, { width: '60%', data: productEntry });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.suppliesService.editProductEntry(result).subscribe(() => {
        this.suppliesService.getSupplies().subscribe(supplies => this.productEntries.data = supplies.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
      })
    });
  }

  deleteProductEntry(id: string) {
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
        this.suppliesService.deleteProductEntry(id).subscribe(() => {
          this.productEntries.data = this.productEntries.data.filter(p => p.id != id);
        })
      }
    });
  }

}
