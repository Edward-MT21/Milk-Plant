import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MilkCollectionFormComponent } from './milk-collection-form.component';

describe('MilkCollectionFormComponent', () => {
  let component: MilkCollectionFormComponent;
  let fixture: ComponentFixture<MilkCollectionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MilkCollectionFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MilkCollectionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
