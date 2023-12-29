import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { BankAccount, MonetaryAmount } from './bankAccount';
import { BankAccountService } from './bankAccount.service';
import { MatDialog } from '@angular/material/dialog';
import { BankAccountDialogComponent } from '../bank-account-dialog/bank-account-dialog.component';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-bank-account',
  templateUrl: './bank-account.component.html',
  styleUrls: ['./bank-account.component.scss']
})
export class BankAccountComponent {

  displayedColumns: string[] = ['name', 'description', 'balance', 'dispositionLimit'];
  bankAccounts = new MatTableDataSource<BankAccount>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private bankAccountService: BankAccountService, private dialog: MatDialog) {

  }

  ngOnInit() {
    this.bankAccounts.paginator = this.paginator;
    this.bankAccountService.getAllBankAccounts().subscribe(bankAccounts => this.bankAccounts.data = bankAccounts);
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.bankAccounts.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(BankAccountDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      const bankAccount = new BankAccount(uuidv4(), result.name, result.description, new MonetaryAmount(result.balance), new MonetaryAmount(result.dispositionLimit), []);
      this.bankAccountService.createBankAccount(bankAccount).subscribe(() => {
        this.bankAccounts.data.push(bankAccount)
        this.bankAccounts.data = this.bankAccounts.data;
      })
    });
  }

}
