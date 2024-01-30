import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuppliesDialogComponent } from './supplies-dialog.component';

describe('SuppliesDialogComponent', () => {
  let component: SuppliesDialogComponent;
  let fixture: ComponentFixture<SuppliesDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuppliesDialogComponent]
    });
    fixture = TestBed.createComponent(SuppliesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
