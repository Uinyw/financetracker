import { Component, ViewChild } from '@angular/core';
import { OneTimeTransaction, Transfer } from './oneTimeTransaction';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { OneTimeTransactionService } from './oneTimeTransaction.service';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { OneTimeTransactionDialogComponent } from '../one-time-transaction-dialog/one-time-transaction-dialog.component';
import { BankAccountService } from '../bank-account/bankAccount.service';
import { BankAccount } from '../bank-account/bankAccount';

@Component({
  selector: 'app-one-time-transaction',
  templateUrl: './one-time-transaction.component.html',
  styleUrls: ['./one-time-transaction.component.scss']
})
export class OneTimeTransactionComponent {


  displayedColumns: string[] = ['name', 'description', 'type', 'amount', 'date', 'transfer', 'transferStatus', 'labels', 'actions'];
  transactions = new MatTableDataSource<OneTimeTransaction>();
  bankAccounts: BankAccount[] = []

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private transactionService: OneTimeTransactionService,
              private bankAccountService: BankAccountService,
              private dialog: MatDialog) {
    this.bankAccountService.getAllBankAccounts().subscribe(result => this.bankAccounts = result)
  }

  ngOnInit() {
    this.transactions.paginator = this.paginator;
    this.transactionService.getAllOneTimeTransactions().subscribe(transactions => this.transactions.data = transactions.sort((a, b) => a.name.localeCompare(b.name)));
  }

  transferToString(transfer: Transfer) {
      const source = transfer.sourceBankAccountId != null ? this.bankAccounts.filter(a => a.id == transfer.sourceBankAccountId)[0].name : transfer.externalSourceId;
      const target = transfer.targetBankAccountId != null ? this.bankAccounts.filter(a => a.id == transfer.targetBankAccountId)[0].name : transfer.externalTargetId;
      return source + " - " + target;
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.transactions.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  transfer(transaction: OneTimeTransaction) {
    this.transactionService.transferOneTimeTransaction(transaction.id).subscribe(() => {
      this.transactionService.getAllOneTimeTransactions().subscribe(transactions => this.transactions.data = transactions.sort((a, b) => a.name.localeCompare(b.name)));
    })
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(OneTimeTransactionDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.transactionService.createOneTimeTransaction(result).subscribe(() => {
        this.transactions.data.push(result)
        this.transactions.data = this.transactions.data.sort((a, b) => a.name.localeCompare(b.name));
      })
    });
  }

  openEditDialog(transaction: OneTimeTransaction): void {
    const dialogRef = this.dialog.open(OneTimeTransactionDialogComponent, { width: '60%', data: transaction });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.transactionService.editOneTimeTransaction(result).subscribe(() => {
        const resultingTransactions = this.transactions.data.filter(a => a.id != result.id)
        resultingTransactions.push(result)
        this.transactions.data = resultingTransactions.sort((a, b) => a.name.localeCompare(b.name));
      })
    });
  }

  deleteTransaction(id: string) {
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
        this.transactionService.deleteOneTimeTransaction(id).subscribe(() => {
          this.transactions.data = this.transactions.data.filter(t => t.id != id);
        })
      }
    });
  }


}
