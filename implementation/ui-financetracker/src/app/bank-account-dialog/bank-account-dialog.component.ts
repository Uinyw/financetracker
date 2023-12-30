import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BankAccount } from '../bank-account/bankAccount';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-bank-account-dialog',
  templateUrl: './bank-account-dialog.component.html',
  styleUrls: ['./bank-account-dialog.component.scss']
})
export class BankAccountDialogComponent {

  editMode: boolean
  bankAccount: BankAccount
  labels: string = ""

  constructor(@Inject(MAT_DIALOG_DATA) private data: BankAccount, private dialogRef: MatDialogRef<BankAccountDialogComponent>) {
    this.editMode = data != null

    if (this.editMode) {
      this.bankAccount = data;
      this.labels = data != null ? data.labels.join(",") : "";
    } else {
      this.bankAccount = {
        id: uuidv4(),
        name: "",
        description: "",
        balance: {
          amount: 0
        },
        dispositionLimit: {
          amount: 0
        },
        labels: []
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingBankAccount() {
    this.bankAccount.labels = this.labels != null ? this.labels.split(",") : []
    return this.bankAccount;
  }

}
