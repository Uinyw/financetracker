import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecurringTransactionComponent } from './recurring-transaction.component';

describe('RecurringTransactionComponent', () => {
  let component: RecurringTransactionComponent;
  let fixture: ComponentFixture<RecurringTransactionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecurringTransactionComponent]
    });
    fixture = TestBed.createComponent(RecurringTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
