import { Component, ViewChild } from '@angular/core';
import { ProductEntry } from './shoppingCart';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { ShoppingCartService } from './shoppingCart.service';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { ShoppingCartDialogComponent } from '../shopping-cart-dialog/shopping-cart-dialog.component';
import { BankAccount } from '../bank-account/bankAccount';
import { BankAccountService } from '../bank-account/bankAccount.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent {

  displayedColumns: string[] = ['purchased', 'name', 'quantity', 'price', 'actions'];
  productEntries = new MatTableDataSource<ProductEntry>();

  bankAccount: BankAccount | null = null
  bankAccounts: BankAccount[] = []

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private shoppingCartService: ShoppingCartService,
              private bankAccountService: BankAccountService,
              private dialog: MatDialog) {
    bankAccountService.getAllBankAccounts().subscribe(result => {
      this.bankAccounts = result
      this.bankAccount = result.length > 0 ? result[0] : null
    });

  }

  ngOnInit() {
    this.productEntries.paginator = this.paginator;
    this.shoppingCartService.getShoppingCart().subscribe(shoppingCart => this.productEntries.data = shoppingCart.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.productEntries.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(ShoppingCartDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.shoppingCartService.createProductEntry(result).subscribe(() => {
        this.shoppingCartService.getShoppingCart().subscribe(shoppingCart => this.productEntries.data = shoppingCart.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
      })
    });
  }

  openEditDialog(productEntry: ProductEntry): void {
    const dialogRef = this.dialog.open(ShoppingCartDialogComponent, { width: '60%', data: productEntry });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.shoppingCartService.editProductEntry(result).subscribe(() => {
        this.shoppingCartService.getShoppingCart().subscribe(shoppingCart => this.productEntries.data = shoppingCart.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
      })
    });
  }

  deleteBankAccount(id: string) {
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
        this.shoppingCartService.deleteProductEntry(id).subscribe(() => {
          this.productEntries.data = this.productEntries.data.filter(p => p.id != id);
        })
      }
    });
  }

  getTotalPrice(productEntry: ProductEntry){
    return productEntry.quantity * productEntry.product!.price!.amount!;
  }

  setProductPurchased(productEntry: ProductEntry) {
    this.shoppingCartService.markProductEntryAsPurchased(productEntry.id).subscribe(result => productEntry.purchased = true)
  }

  bookPurchasedProductEntries() {
    Swal.fire({
      title: 'Book Purchased Product Entries',
      icon: 'info',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, book it!',
    }).then((result) => {
      if (result.value) {
        this.shoppingCartService.purchaseShoppingCart(this.bankAccount!.id).subscribe(result => {
          this.shoppingCartService.getShoppingCart().subscribe(shoppingCart => this.productEntries.data = shoppingCart.productEntries.sort((a, b) => a.product!.name.localeCompare(b.product!.name)));
        })
      }
    });
  }

}
