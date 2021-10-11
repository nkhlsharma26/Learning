import { TestBed } from '@angular/core/testing';

import { AidService } from './aid.service';

describe('AidService', () => {
  let service: AidService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AidService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
