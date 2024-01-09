import { Component, ViewChild } from '@angular/core';
import { RecurringTransaction } from './recurringTransaction';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { RecurringTransactionService } from './recurringTransaction.service';
import { MatDialog } from '@angular/material/dialog';
import { Transfer } from '../transaction/transaction';
import { BankAccountService } from '../bank-account/bankAccount.service';
import { BankAccount } from '../bank-account/bankAccount';
import Swal from 'sweetalert2';
import { RecurringTransactionDialogComponent } from '../recurring-transaction-dialog/recurring-transaction-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recurring-transaction',
  templateUrl: './recurring-transaction.component.html',
  styleUrls: ['./recurring-transaction.component.scss']
})
export class RecurringTransactionComponent {

  displayedColumns: string[] = ['name', 'description', 'type', 'periodicity', 'fixedAmount', 'startDate', 'transfer', 'labels', 'actions'];
  bankAccounts: BankAccount[] = []
  transactions = new MatTableDataSource<RecurringTransaction>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private transactionService: RecurringTransactionService,
              private bankAccountService: BankAccountService,
              private dialog: MatDialog) {
    this.bankAccountService.getAllBankAccounts().subscribe(result => this.bankAccounts = result)
  }

  ngOnInit() {
    this.transactions.paginator = this.paginator;
    this.transactionService.getAllRecurringTransactions().subscribe(transactions => {
      this.transactions.data = transactions.sort((a, b) => a.name.localeCompare(b.name))
    });
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

  openAddDialog(): void {
    const dialogRef = this.dialog.open(RecurringTransactionDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.transactionService.createRecurringTransaction(result).subscribe(() => {
        this.transactions.data.push(result)
        this.transactions.data = this.transactions.data.sort((a, b) => a.name.localeCompare(b.name));
      })
    });
  }

  openEditDialog(transaction: RecurringTransaction): void {
    const dialogRef = this.dialog.open(RecurringTransactionDialogComponent, { width: '60%', data: transaction });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.transactionService.editRecurringTransaction(result).subscribe(() => {
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
        this.transactionService.deleteRecurringTransaction(id).subscribe(() => {
          this.transactions.data = this.transactions.data.filter(t => t.id != id);
        })
      }
    });
  }

}
