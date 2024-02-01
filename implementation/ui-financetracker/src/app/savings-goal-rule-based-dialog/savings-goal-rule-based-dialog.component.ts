import { Component, Inject } from '@angular/core';
import { AchievementStatus, RuleBasedSavingsGoal, Type } from '../savings-goal-rule-based/ruleBasedSavingsGoal';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-savings-goal-rule-based-dialog',
  templateUrl: './savings-goal-rule-based-dialog.component.html',
  styleUrls: ['./savings-goal-rule-based-dialog.component.scss']
})
export class SavingsGoalRuleBasedDialogComponent {

  editMode: boolean
  savingsGoal: RuleBasedSavingsGoal

  types = Object.values(Type);

  constructor(@Inject(MAT_DIALOG_DATA) private data: RuleBasedSavingsGoal, private dialogRef: MatDialogRef<SavingsGoalRuleBasedDialogComponent>) {
    this.editMode = data != null

    if (this.editMode) {
      this.savingsGoal = data;
    } else {
      this.savingsGoal = {
        id: uuidv4(),
        name: "",
        description: "",
        achievementStatus: AchievementStatus.ACHIEVED,
        type: Type.MATCH_ANY,
        rules: []
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getResultingSavingsGoal() {
    return this.savingsGoal;
  }

}
