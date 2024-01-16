import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionRecordDialogComponent } from './transaction-record-dialog.component';

describe('TransactionRecordDialogComponent', () => {
  let component: TransactionRecordDialogComponent;
  let fixture: ComponentFixture<TransactionRecordDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransactionRecordDialogComponent]
    });
    fixture = TestBed.createComponent(TransactionRecordDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
