import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { RecurringTransaction } from '../recurring-transaction/recurringTransaction';
import { BankAccount } from '../bank-account/bankAccount';
import { MatPaginator } from '@angular/material/paginator';
import { PeriodicalSavingsGoalService } from './periodicalSavingsGoal.service';
import { BankAccountService } from '../bank-account/bankAccount.service';
import { MatDialog } from '@angular/material/dialog';
import { PeriodicalSavingsGoal } from './periodicalSavingsGoal';
import Swal from 'sweetalert2';
import { SavingsGoalPeriodicalDialogComponent } from '../savings-goal-periodical-dialog/savings-goal-periodical-dialog.component';

@Component({
  selector: 'app-savings-goal-periodical',
  templateUrl: './savings-goal-periodical.component.html',
  styleUrls: ['./savings-goal-periodical.component.scss']
})
export class SavingsGoalPeriodicalComponent {

  displayedColumns: string[] = ['name', 'description', 'rate', 'periodicity', 'transfer', 'duration', 'goal', 'achievementStatus', 'actions'];
  bankAccounts: BankAccount[] = []
  savingsGoals = new MatTableDataSource<PeriodicalSavingsGoal>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private savingsGoalService: PeriodicalSavingsGoalService,
              private bankAccountService: BankAccountService,
              private dialog: MatDialog) {
    this.bankAccountService.getAllBankAccounts().subscribe(result => this.bankAccounts = result)
  }

  ngOnInit() {
    this.savingsGoals.paginator = this.paginator;
    this.savingsGoalService.getAllPeriodicalSavingsGoals().subscribe(savingsGoals => {
      this.savingsGoals.data = savingsGoals.sort((a, b) => a.name.localeCompare(b.name))
    });
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.savingsGoals.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(SavingsGoalPeriodicalDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.savingsGoalService.createPeriodicalSavingsGoal(result).subscribe(() => {
        this.savingsGoals.data.push(result)
        this.savingsGoals.data = this.savingsGoals.data.sort((a, b) => a.name.localeCompare(b.name));
      })
    });
  }

  openEditDialog(transaction: RecurringTransaction): void {
    /*const dialogRef = this.dialog.open(RecurringTransactionDialogComponent, { width: '60%', data: transaction });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.transactionService.editRecurringTransaction(result).subscribe(() => {
        const resultingTransactions = this.transactions.data.filter(a => a.id != result.id)
        resultingTransactions.push(result)
        this.transactions.data = resultingTransactions.sort((a, b) => a.name.localeCompare(b.name));
      })
    });*/
  }

  deleteSavingsGoal(id: string) {
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
        this.savingsGoalService.deletePeriodicalSavingsGoal(id).subscribe(() => {
          this.savingsGoals.data = this.savingsGoals.data.filter(t => t.id != id);
        })
      }
    });
  }

  getTransfer(goal: PeriodicalSavingsGoal) {
    const source = this.bankAccounts.filter(a => a.id == goal.sourceBankAccountId)[0];
    const target = this.bankAccounts.filter(a => a.id == goal.targetBankAccountId)[0];
    return source.name + " - " + target.name;
}

}
