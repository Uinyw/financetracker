import { Component, Inject } from '@angular/core';
import { PeriodicalSavingsGoal } from '../savings-goal-periodical/periodicalSavingsGoal';
import { BankAccount } from '../bank-account/bankAccount';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BankAccountService } from '../bank-account/bankAccount.service';
import { v4 as uuidv4 } from 'uuid';
import { AchievementStatus } from '../savings-goal-rule-based/ruleBasedSavingsGoal';
import { Periodicity } from '../transaction/transaction';

@Component({
  selector: 'app-savings-goal-periodical-dialog',
  templateUrl: './savings-goal-periodical-dialog.component.html',
  styleUrls: ['./savings-goal-periodical-dialog.component.scss']
})
export class SavingsGoalPeriodicalDialogComponent {

  editMode: boolean
  savingsGoal: PeriodicalSavingsGoal
  date: Date = new Date();
  bankAccounts: BankAccount[] = [];
  startDate: Date = new Date();
  endDate: Date | null = null;

  periodicities = Object.values(Periodicity);

  constructor(@Inject(MAT_DIALOG_DATA) private data: PeriodicalSavingsGoal,
              private dialogRef: MatDialogRef<SavingsGoalPeriodicalDialogComponent>,
              private bankAccountService: BankAccountService) {

    this.editMode = data != null
    this.bankAccountService.getAllBankAccounts().subscribe(result => this.bankAccounts = result)

    if (this.editMode) {
      this.savingsGoal = data;
    } else {
      this.savingsGoal = {
        id: uuidv4(),
        name: "",
        description: "",
        sourceBankAccountId: "",
        targetBankAccountId: "",
        achievementStatus: AchievementStatus.IN_PROGRESS,
        periodicity: Periodicity.MONTHLY,
        goal: {
          amount: null
        },
        recurringAmount: {
          amount: null
        },
        recurringRate: null,
        duration: "",
        savingsRecords: []
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingSavingsGoal() {
    this.savingsGoal.duration = this.startDate.toISOString().slice(0, 10) + ";" + (this.endDate ? this.endDate.toISOString().slice(0, 10) : "");
    return this.savingsGoal;
  }

}
