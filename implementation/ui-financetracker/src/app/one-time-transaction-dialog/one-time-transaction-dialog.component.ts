import { Component, Inject } from '@angular/core';
import { OneTimeTransaction } from '../one-time-transaction/oneTimeTransaction';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { v4 as uuidv4 } from 'uuid';
import { BankAccountService } from '../bank-account/bankAccount.service';
import { BankAccount } from '../bank-account/bankAccount';
import { TransferStatus, Type } from '../transaction/transaction';

@Component({
  selector: 'app-one-time-transaction-dialog',
  templateUrl: './one-time-transaction-dialog.component.html',
  styleUrls: ['./one-time-transaction-dialog.component.scss']
})
export class OneTimeTransactionDialogComponent {

  editMode: boolean
  transaction: OneTimeTransaction
  date: Date = new Date();
  bankAccounts: BankAccount[] = [];

  types = Object.values(Type);
  labels: string = ""

  constructor(@Inject(MAT_DIALOG_DATA) private data: OneTimeTransaction,
              private dialogRef: MatDialogRef<OneTimeTransactionDialogComponent>,
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
        amount: {
          amount: 0
        },
        date: "",
        transferStatus: TransferStatus.INITIAL
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingTransaction() {
    this.transaction.date = this.date.toISOString().slice(0, 10)
    this.transaction.labels = this.labels != null ? this.labels.split(",") : []
    return this.transaction;
  }

}
