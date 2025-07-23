import { TestBed } from '@angular/core/testing';

import { MilkCollectionService } from './milk-collection.service';

describe('MilkCollectionService', () => {
  let service: MilkCollectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MilkCollectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
