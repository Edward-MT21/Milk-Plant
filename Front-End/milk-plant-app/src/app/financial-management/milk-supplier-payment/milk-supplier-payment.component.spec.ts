import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MilkSupplierPaymentComponent } from './milk-supplier-payment.component';

describe('MilkSupplierPaymentComponent', () => {
  let component: MilkSupplierPaymentComponent;
  let fixture: ComponentFixture<MilkSupplierPaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MilkSupplierPaymentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MilkSupplierPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
