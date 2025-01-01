import { TestBed } from '@angular/core/testing';

import { WindFarmService } from './wind-farm.service';

describe('WindFarmService', () => {
  let service: WindFarmService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WindFarmService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
