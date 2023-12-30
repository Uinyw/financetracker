import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneTimeTransactionDialogComponent } from './one-time-transaction-dialog.component';

describe('OneTimeTransactionDialogComponent', () => {
  let component: OneTimeTransactionDialogComponent;
  let fixture: ComponentFixture<OneTimeTransactionDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OneTimeTransactionDialogComponent]
    });
    fixture = TestBed.createComponent(OneTimeTransactionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
