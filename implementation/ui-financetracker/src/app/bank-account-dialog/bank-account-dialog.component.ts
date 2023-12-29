import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { BankAccount } from '../bank-account/bankAccount';

@Component({
  selector: 'app-bank-account-dialog',
  templateUrl: './bank-account-dialog.component.html',
  styleUrls: ['./bank-account-dialog.component.scss']
})
export class BankAccountDialogComponent {

  constructor(private dialogRef: MatDialogRef<BankAccountDialogComponent>) {
    
  }

  bankAccount = {
    name: "",
    description: "",
    balance: null,
    dispositionLimit: null
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
