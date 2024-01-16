import { Component, Inject } from '@angular/core';
import { RecurringTransaction, TransactionRecord } from '../recurring-transaction/recurringTransaction';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { v4 as uuidv4 } from 'uuid';
import { TransferStatus } from '../transaction/transaction';

@Component({
  selector: 'app-transaction-record-dialog',
  templateUrl: './transaction-record-dialog.component.html',
  styleUrls: ['./transaction-record-dialog.component.scss']
})
export class TransactionRecordDialogComponent {

  transaction: RecurringTransaction
  record: TransactionRecord
  date: Date = new Date();

  constructor(@Inject(MAT_DIALOG_DATA) private data: RecurringTransaction,
              private dialogRef: MatDialogRef<TransactionRecordDialogComponent>) {

    this.transaction = data;

    this.record = {
      id: uuidv4(),
      date: "",
      amount: {
        amount: 0
      },
      transferStatus: TransferStatus.INITIAL
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingRecord() {
    this.record.date = this.date.toISOString().slice(0, 10)
    return this.record;
  }

}
