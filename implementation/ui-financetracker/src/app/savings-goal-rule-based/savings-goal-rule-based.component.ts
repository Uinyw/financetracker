import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { RuleBasedSavingsGoal } from './ruleBasedSavingsGoal';
import { MatPaginator } from '@angular/material/paginator';
import { RuleBasedSavingsGoalService } from './ruleBasedSavingsGoal.service';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { SavingsGoalRuleBasedDialogComponent } from '../savings-goal-rule-based-dialog/savings-goal-rule-based-dialog.component';

@Component({
  selector: 'app-savings-goal-rule-based',
  templateUrl: './savings-goal-rule-based.component.html',
  styleUrls: ['./savings-goal-rule-based.component.scss']
})
export class SavingsGoalRuleBasedComponent {

  displayedColumns: string[] = ['name', 'description', 'type', 'status', 'actions'];
  savingsGoals = new MatTableDataSource<RuleBasedSavingsGoal>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private savingsGoalService: RuleBasedSavingsGoalService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.savingsGoals.paginator = this.paginator;
    this.savingsGoalService.getAllRuleBasedSavingsGoals().subscribe(savingsGoals => {
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
    const dialogRef = this.dialog.open(SavingsGoalRuleBasedDialogComponent, { width: '60%' });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.savingsGoalService.createRuleBasedSavingsGoal(result).subscribe(() => {
        this.savingsGoals.data.push(result)
        this.savingsGoals.data = this.savingsGoals.data.sort((a, b) => a.name.localeCompare(b.name));
      })
    });
  }

  openEditDialog(savingsGoal: RuleBasedSavingsGoal): void {
    const dialogRef = this.dialog.open(SavingsGoalRuleBasedDialogComponent, { width: '60%', data: savingsGoal });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      this.savingsGoalService.editRuleBasedSavingsGoal(result).subscribe(() => {
        const resultingGoals = this.savingsGoals.data.filter(s => s.id != result.id)
        resultingGoals.push(result)
        this.savingsGoals.data = resultingGoals.sort((a, b) => a.name.localeCompare(b.name));
      })
    });
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
        this.savingsGoalService.deleteRuleBasedSavingsGoal(id).subscribe(() => {
          this.savingsGoals.data = this.savingsGoals.data.filter(t => t.id != id);
        })
      }
    });
  }

}
