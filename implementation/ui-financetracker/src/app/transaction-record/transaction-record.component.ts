import { Component, ViewChild } from '@angular/core';
import { RecurringTransaction, TransactionRecord } from '../recurring-transaction/recurringTransaction';
import { MatPaginator } from '@angular/material/paginator';
import { RecurringTransactionService } from '../recurring-transaction/recurringTransaction.service';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';
import { TransactionRecordDialogComponent } from '../transaction-record-dialog/transaction-record-dialog.component';

@Component({
  selector: 'app-transaction-record',
  templateUrl: './transaction-record.component.html',
  styleUrls: ['./transaction-record.component.scss']
})
export class TransactionRecordComponent {

  displayedColumns: string[] = ['date', 'amount', 'transferStatus', 'actions'];
  transaction: RecurringTransaction | undefined;
  transactionRecords = new MatTableDataSource<TransactionRecord>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private transactionService: RecurringTransactionService,
              private dialog: MatDialog,
              route: ActivatedRoute) {
    transactionService.getRecurringTransaction(route.snapshot.paramMap.get("id")!).subscribe(t => {
      this.transaction = t;
      this.transactionRecords.data = t.transactionRecords;
    });
  }

  ngOnInit() {
    this.transactionRecords.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.transactionRecords.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  transfer(record: TransactionRecord) {
    this.transactionService.transferTransactionRecord(this.transaction?.id!, record.id).subscribe(() => {
      this.transactionService.getRecurringTransaction(this.transaction?.id!).subscribe(t => {
        this.transactionRecords.data = t.transactionRecords;
      });
    })
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(TransactionRecordDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.transactionService.createTransactionRecord(this.transaction?.id!, result).subscribe(() => {
        this.transactionRecords.data.push(result)
        this.transactionRecords.data = this.transactionRecords.data;
      })
    });
  }

  deleteTransactionRecord(id: string) {
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
        this.transactionService.deleteTransactionRecord(this.transaction?.id!, id).subscribe(() => {
          this.transactionRecords.data = this.transactionRecords.data.filter(r => r.id != id);
        })
      }
    });
  }

}
