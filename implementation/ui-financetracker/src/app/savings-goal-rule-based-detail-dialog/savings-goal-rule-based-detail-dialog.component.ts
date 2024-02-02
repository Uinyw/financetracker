import { Component, Inject } from '@angular/core';
import { Rule, RuleBasedSavingsGoal, RuleType } from '../savings-goal-rule-based/ruleBasedSavingsGoal';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { v4 as uuidv4 } from 'uuid';
import { BankAccount } from '../bank-account/bankAccount';
import { BankAccountService } from '../bank-account/bankAccount.service';

@Component({
  selector: 'app-savings-goal-rule-based-detail-dialog',
  templateUrl: './savings-goal-rule-based-detail-dialog.component.html',
  styleUrls: ['./savings-goal-rule-based-detail-dialog.component.scss']
})
export class SavingsGoalRuleBasedDetailDialogComponent {

  editMode: boolean
  rule: Rule
  types = Object.values(RuleType);

  bankAccounts: BankAccount[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) private data: Rule,
              private dialogRef: MatDialogRef<SavingsGoalRuleBasedDetailDialogComponent>,
              private bankAccountService: BankAccountService) {

    this.bankAccountService.getAllBankAccounts().subscribe(result => this.bankAccounts = result)
    this.editMode = data != null

    if (this.editMode) {
      this.rule = data;
    } else {
      this.rule = {
        id: uuidv4(),
        description: "",
        bankAccountId: "",
        target: {
          amount: 0.0
        },
        type: RuleType.EQUALS
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingRule() {
    return this.rule;
  }

}
