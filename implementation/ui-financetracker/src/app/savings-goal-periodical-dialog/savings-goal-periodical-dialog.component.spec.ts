import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingsGoalPeriodicalDialogComponent } from './savings-goal-periodical-dialog.component';

describe('SavingsGoalPeriodicalDialogComponent', () => {
  let component: SavingsGoalPeriodicalDialogComponent;
  let fixture: ComponentFixture<SavingsGoalPeriodicalDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SavingsGoalPeriodicalDialogComponent]
    });
    fixture = TestBed.createComponent(SavingsGoalPeriodicalDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
