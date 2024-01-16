import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { BankAccount } from './bankAccount';
import Swal from 'sweetalert2';
import { BankAccountService } from './bankAccount.service';
import { MatDialog } from '@angular/material/dialog';
import { BankAccountDialogComponent } from '../bank-account-dialog/bank-account-dialog.component';

@Component({
  selector: 'app-bank-account',
  templateUrl: './bank-account.component.html',
  styleUrls: ['./bank-account.component.scss']
})
export class BankAccountComponent {

  displayedColumns: string[] = ['name', 'description', 'balance', 'dispositionLimit', 'labels', 'actions'];
  bankAccounts = new MatTableDataSource<BankAccount>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private bankAccountService: BankAccountService, private dialog: MatDialog) {

  }

  ngOnInit() {
    this.bankAccounts.paginator = this.paginator;
    this.bankAccountService.getAllBankAccounts().subscribe(bankAccounts => this.bankAccounts.data = bankAccounts.sort((a, b) => a.name.localeCompare(b.name)));
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.bankAccounts.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(BankAccountDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.bankAccountService.createBankAccount(result).subscribe(() => {
        this.bankAccounts.data.push(result)
        this.bankAccounts.data = this.bankAccounts.data.sort((a, b) => a.name.localeCompare(b.name));
      })
    });
  }

  openEditDialog(bankAccount: BankAccount): void {
    const dialogRef = this.dialog.open(BankAccountDialogComponent, { width: '60%', data: bankAccount });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.bankAccountService.editBankAccount(result).subscribe(() => {
        const resultingAccounts = this.bankAccounts.data.filter(account => account.id != bankAccount.id)
        resultingAccounts.push(result)
        this.bankAccounts.data = resultingAccounts.sort((a, b) => a.name.localeCompare(b.name));
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
        this.bankAccountService.deleteBankAccount(id).subscribe(() => {
          this.bankAccounts.data = this.bankAccounts.data.filter(account => account.id != id);
        })
      }
    });
  }

}
