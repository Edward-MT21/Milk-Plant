import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MilkCollectionComponent } from './milk-collection.component';

describe('MilkCollectionComponent', () => {
  let component: MilkCollectionComponent;
  let fixture: ComponentFixture<MilkCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MilkCollectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MilkCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
