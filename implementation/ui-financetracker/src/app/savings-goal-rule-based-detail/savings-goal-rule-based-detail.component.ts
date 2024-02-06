import { Component, ViewChild } from '@angular/core';
import { Rule, RuleBasedSavingsGoal } from '../savings-goal-rule-based/ruleBasedSavingsGoal';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { RuleBasedSavingsGoalService } from '../savings-goal-rule-based/ruleBasedSavingsGoal.service';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { SavingsGoalRuleBasedDetailDialogComponent } from '../savings-goal-rule-based-detail-dialog/savings-goal-rule-based-detail-dialog.component';
import { BankAccount } from '../bank-account/bankAccount';
import { BankAccountService } from '../bank-account/bankAccount.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-savings-goal-rule-based-detail',
  templateUrl: './savings-goal-rule-based-detail.component.html',
  styleUrls: ['./savings-goal-rule-based-detail.component.scss']
})
export class SavingsGoalRuleBasedDetailComponent {

  displayedColumns: string[] = ['bankAccount', 'target', 'type', 'description', 'actions'];
  rules = new MatTableDataSource<Rule>();

  savingsGoal: RuleBasedSavingsGoal | null = null;
  bankAccounts: BankAccount[] = [];

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(private savingsGoalService: RuleBasedSavingsGoalService,
              private bankAccountService: BankAccountService,
              private dialog: MatDialog,
              private route: ActivatedRoute) {
    this.savingsGoalService.getRuleBasedSavingsGoalById(route.snapshot.paramMap.get("id")!).subscribe(result => {
      this.savingsGoal = result
      this.rules.data = this.savingsGoal.rules
    })

    this.bankAccountService.getAllBankAccounts().subscribe(result => this.bankAccounts = result)
  }

  getNameForBankAccount(id: string) {
    const accounts = this.bankAccounts.filter(b => b.id == id);
    return accounts.length == 1 ? accounts[0].name : ""
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
        this.rules.data = this.rules.data.sort((a, b) => a.bankAccountId.localeCompare(b.bankAccountId));
      })

    });
  }

  openEditDialog(rule: Rule): void {
    const dialogRef = this.dialog.open(SavingsGoalRuleBasedDetailDialogComponent, { width: '60%', data: rule });

    dialogRef.afterClosed().subscribe(result => {
      if (result == null) {
        return;
      }

      const resultingRules = this.savingsGoal!.rules.filter(r => r.id != result.id);
      resultingRules.push(result);
      this.savingsGoal!.rules = resultingRules;

      this.savingsGoalService.editRuleBasedSavingsGoal(this.savingsGoal!).subscribe(() => {
        this.rules.data = this.rules.data.sort((a, b) => a.bankAccountId.localeCompare(b.bankAccountId));
      })
    });
  }

  deleteRule(id: string) {
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
        const resultingRules = this.savingsGoal!.rules.filter(r => r.id != id);
        this.savingsGoal!.rules = resultingRules;

        this.savingsGoalService.editRuleBasedSavingsGoal(this.savingsGoal!).subscribe(() => {
          this.rules.data = this.savingsGoal!.rules;
          this.rules.data = this.rules.data.sort((a, b) => a.bankAccountId.localeCompare(b.bankAccountId));
        })
      }
    });
  }

}
