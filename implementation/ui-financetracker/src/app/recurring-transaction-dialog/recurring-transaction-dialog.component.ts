import { Component, Inject } from '@angular/core';
import { RecurringTransaction } from '../recurring-transaction/recurringTransaction';
import { BankAccount } from '../bank-account/bankAccount';
import { Periodicity, Type } from '../transaction/transaction';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BankAccountService } from '../bank-account/bankAccount.service';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-recurring-transaction-dialog',
  templateUrl: './recurring-transaction-dialog.component.html',
  styleUrls: ['./recurring-transaction-dialog.component.scss']
})
export class RecurringTransactionDialogComponent {

  editMode: boolean
  transaction: RecurringTransaction
  startDate: Date = new Date();
  bankAccounts: BankAccount[] = [];

  types = Object.values(Type);
  periodicities = Object.values(Periodicity);
  labels: string = ""

  constructor(@Inject(MAT_DIALOG_DATA) private data: RecurringTransaction,
              private dialogRef: MatDialogRef<RecurringTransactionDialogComponent>,
              private bankAccountService: BankAccountService) {

    this.editMode = data != null
    this.bankAccountService.getAllBankAccounts().subscribe(result => this.bankAccounts = result)

    if (this.editMode) {
      this.transaction = data;
      this.labels = data != null ? data.labels.join(",") : "";
    } else {
      this.transaction = {
        id: uuidv4(),
        name: "",
        description: "",
        type: Type.INCOME,
        labels: [],
        transfer: {
          sourceBankAccountId: null,
          externalSourceId: null,
          targetBankAccountId: null,
          externalTargetId: null
        },
        periodicity: Periodicity.MONTHLY,
        startDate: "",
        fixedAmount: {
          amount: null
        },
        transactionRecords: []
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingTransaction() {
    this.transaction.startDate = this.startDate.toISOString().slice(0, 10)
    
    this.transaction.labels = this.labels != null ? this.labels.split(",") : []
    return this.transaction;
  }

}
