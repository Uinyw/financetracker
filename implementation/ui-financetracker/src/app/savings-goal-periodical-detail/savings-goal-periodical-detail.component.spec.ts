import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavingsGoalPeriodicalDetailComponent } from './savings-goal-periodical-detail.component';

describe('SavingsGoalPeriodicalDetailComponent', () => {
  let component: SavingsGoalPeriodicalDetailComponent;
  let fixture: ComponentFixture<SavingsGoalPeriodicalDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SavingsGoalPeriodicalDetailComponent]
    });
    fixture = TestBed.createComponent(SavingsGoalPeriodicalDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
