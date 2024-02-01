import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingsGoalRuleBasedDetailComponent } from './savings-goal-rule-based-detail.component';

describe('SavingsGoalRuleBasedDetailComponent', () => {
  let component: SavingsGoalRuleBasedDetailComponent;
  let fixture: ComponentFixture<SavingsGoalRuleBasedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SavingsGoalRuleBasedDetailComponent]
    });
    fixture = TestBed.createComponent(SavingsGoalRuleBasedDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
