import { Component, ViewChild } from '@angular/core';
import { PeriodicalSavingsGoal, SavingsRecord } from '../savings-goal-periodical/periodicalSavingsGoal';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { PeriodicalSavingsGoalService } from '../savings-goal-periodical/periodicalSavingsGoal.service';

@Component({
  selector: 'app-savings-goal-periodical-detail',
  templateUrl: './savings-goal-periodical-detail.component.html',
  styleUrls: ['./savings-goal-periodical-detail.component.scss']
})
export class SavingsGoalPeriodicalDetailComponent {

  displayedColumns: string[] = ['date', 'amount', 'achievementStatus'];
  savingsGoal: PeriodicalSavingsGoal | undefined;
  savingsRecords = new MatTableDataSource<SavingsRecord>();

  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator;

  constructor(savingsGoalService: PeriodicalSavingsGoalService,
              route: ActivatedRoute) {
      savingsGoalService.getPeriodicalSavingsGoalById(route.snapshot.paramMap.get("id")!).subscribe(s => {
      this.savingsGoal = s;
      this.savingsRecords.data = s.savingsRecords;
    });
  }

  ngOnInit() {
    this.savingsRecords.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    if (event.target == null) {
      return;
    }
    this.savingsRecords.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

}
