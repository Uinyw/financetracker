import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingsGoalPeriodicalComponent } from './savings-goal-periodical.component';

describe('SavingsGoalPeriodicalComponent', () => {
  let component: SavingsGoalPeriodicalComponent;
  let fixture: ComponentFixture<SavingsGoalPeriodicalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SavingsGoalPeriodicalComponent]
    });
    fixture = TestBed.createComponent(SavingsGoalPeriodicalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
