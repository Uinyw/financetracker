import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingsGoalRuleBasedDetailDialogComponent } from './savings-goal-rule-based-detail-dialog.component';

describe('SavingsGoalRuleBasedDetailDialogComponent', () => {
  let component: SavingsGoalRuleBasedDetailDialogComponent;
  let fixture: ComponentFixture<SavingsGoalRuleBasedDetailDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SavingsGoalRuleBasedDetailDialogComponent]
    });
    fixture = TestBed.createComponent(SavingsGoalRuleBasedDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
