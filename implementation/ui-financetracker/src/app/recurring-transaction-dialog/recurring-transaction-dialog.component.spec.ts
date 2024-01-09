import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecurringTransactionDialogComponent } from './recurring-transaction-dialog.component';

describe('RecurringTransactionDialogComponent', () => {
  let component: RecurringTransactionDialogComponent;
  let fixture: ComponentFixture<RecurringTransactionDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecurringTransactionDialogComponent]
    });
    fixture = TestBed.createComponent(RecurringTransactionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
