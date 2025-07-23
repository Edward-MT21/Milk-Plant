import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MilkSupplierComponent } from './milk-supplier.component';

describe('MilkSupplierComponent', () => {
  let component: MilkSupplierComponent;
  let fixture: ComponentFixture<MilkSupplierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MilkSupplierComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MilkSupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
