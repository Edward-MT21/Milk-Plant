import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagePersonComponent } from './manage-person.component';

describe('ManagePersonComponent', () => {
  let component: ManagePersonComponent;
  let fixture: ComponentFixture<ManagePersonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagePersonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagePersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
