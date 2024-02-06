import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingsGoalRuleBasedComponent } from './savings-goal-rule-based.component';

describe('SavingsGoalRuleBasedComponent', () => {
  let component: SavingsGoalRuleBasedComponent;
  let fixture: ComponentFixture<SavingsGoalRuleBasedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SavingsGoalRuleBasedComponent]
    });
    fixture = TestBed.createComponent(SavingsGoalRuleBasedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
