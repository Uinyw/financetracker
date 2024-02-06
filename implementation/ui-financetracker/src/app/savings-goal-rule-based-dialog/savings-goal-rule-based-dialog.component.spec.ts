import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingsGoalRuleBasedDialogComponent } from './savings-goal-rule-based-dialog.component';

describe('SavingsGoalRuleBasedDialogComponent', () => {
  let component: SavingsGoalRuleBasedDialogComponent;
  let fixture: ComponentFixture<SavingsGoalRuleBasedDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SavingsGoalRuleBasedDialogComponent]
    });
    fixture = TestBed.createComponent(SavingsGoalRuleBasedDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
