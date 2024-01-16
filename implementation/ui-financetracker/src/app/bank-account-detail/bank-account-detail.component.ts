import { Component, ViewChild } from '@angular/core';
import { BankAccount } from '../bank-account/bankAccount';
import { Transaction, Transfer } from '../transaction/transaction';
import { MatTableDataSource } from '@angular/material/table';
import { OneTimeTransactionService } from '../one-time-transaction/oneTimeTransaction.service';
import { BankAccountService } from '../bank-account/bankAccount.service';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';
import { RecurringTransactionService } from '../recurring-transaction/recurringTransaction.service';

@Component({
  selector: 'app-bank-account-detail',
  templateUrl: './bank-account-detail.component.html',
  styleUrls: ['./bank-account-detail.component.scss']
})
export class BankAccountDetailComponent {

  displayedColumns: string[] = ['name', 'date', 'amount', 'transfer'];
  transactions = new MatTableDataSource<Transaction>();
  bankAccounts: BankAccount[] = []

  bankAccount: BankAccount | undefined;

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private oneTimeTransactionService: OneTimeTransactionService,
              private recurringTransactionService: RecurringTransactionService,
              private bankAccountService: BankAccountService,
              route: ActivatedRoute) {
    this.bankAccountService.getAllBankAccounts().subscribe(result => {
      this.bankAccounts = result;
      this.bankAccount = this.bankAccounts.filter(account => account.id == route.snapshot.paramMap.get("id")!)[0];

      this.oneTimeTransactionService.getAllOneTimeTransactionsBySource(this.bankAccount.id).subscribe(transactions => {
        this.transactions.data = this.transactions.data.concat(transactions.map(t => new Transaction(t.name, t.date, t.transfer, t.amount, false)));
      });

      this.oneTimeTransactionService.getAllOneTimeTransactionsByTarget(this.bankAccount.id).subscribe(transactions => {
        this.transactions.data = this.transactions.data.concat(transactions.map(t => new Transaction(t.name, t.date, t.transfer, t.amount, true)));
      });

      this.recurringTransactionService.getAllRecurringTransactionsBySource(this.bankAccount.id).subscribe(transactions => {
        let resultingTransaction: Transaction[] = []
        transactions.forEach(transaction => {
          transaction.transactionRecords.forEach(record => resultingTransaction.push(new Transaction(transaction.name, record.date, transaction.transfer, record.amount, false)))
        })

        this.transactions.data = this.transactions.data.concat(resultingTransaction);
      });

      this.recurringTransactionService.getAllRecurringTransactionsByTarget(this.bankAccount.id).subscribe(transactions => {
        let resultingTransaction: Transaction[] = []
        transactions.forEach(transaction => {
          transaction.transactionRecords.forEach(record => resultingTransaction.push(new Transaction(transaction.name, record.date, transaction.transfer, record.amount, true)))
        })

        this.transactions.data = this.transactions.data.concat(resultingTransaction);
      });
    })
    
  }

  ngOnInit() {
    this.transactions.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.transactions.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  transferToString(transfer: Transfer) {
    const source = transfer.sourceBankAccountId != null ? this.bankAccounts.filter(a => a.id == transfer.sourceBankAccountId)[0].name : transfer.externalSourceId;
    const target = transfer.targetBankAccountId != null ? this.bankAccounts.filter(a => a.id == transfer.targetBankAccountId)[0].name : transfer.externalTargetId;
    return source + " - " + target;
}

}
