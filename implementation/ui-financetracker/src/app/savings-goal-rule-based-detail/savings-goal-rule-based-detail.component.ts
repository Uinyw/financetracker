import { Component, ViewChild } from '@angular/core';
import { Rule, RuleBasedSavingsGoal } from '../savings-goal-rule-based/ruleBasedSavingsGoal';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { RuleBasedSavingsGoalService } from '../savings-goal-rule-based/ruleBasedSavingsGoal.service';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { SavingsGoalRuleBasedDetailDialogComponent } from '../savings-goal-rule-based-detail-dialog/savings-goal-rule-based-detail-dialog.component';

@Component({
  selector: 'app-savings-goal-rule-based-detail',
  templateUrl: './savings-goal-rule-based-detail.component.html',
  styleUrls: ['./savings-goal-rule-based-detail.component.scss']
})
export class SavingsGoalRuleBasedDetailComponent {

  displayedColumns: string[] = ['bankAccount', 'target', 'type', 'description', 'actions'];
  rules = new MatTableDataSource<Rule>();

  savingsGoal: RuleBasedSavingsGoal | null = null;

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private savingsGoalService: RuleBasedSavingsGoalService,
              private dialog: MatDialog,
              private route: ActivatedRoute) {
    this.savingsGoalService.getRuleBasedSavingsGoalById(route.snapshot.paramMap.get("id")!).subscribe(result => this.savingsGoal = result)
  }

  ngOnInit() {
    this.rules.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.rules.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(SavingsGoalRuleBasedDetailDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.savingsGoal?.rules.push(result);

      this.savingsGoalService.editRuleBasedSavingsGoal(this.savingsGoal!).subscribe(() => {
        this.rules.data.push(result)
        this.rules.data = this.rules.data.sort((a, b) => a.bankAccountID.localeCompare(b.bankAccountID));
      })

    });
  }

}
