import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneTimeTransactionComponent } from './one-time-transaction.component';

describe('OneTimeTransactionComponent', () => {
  let component: OneTimeTransactionComponent;
  let fixture: ComponentFixture<OneTimeTransactionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OneTimeTransactionComponent]
    });
    fixture = TestBed.createComponent(OneTimeTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
