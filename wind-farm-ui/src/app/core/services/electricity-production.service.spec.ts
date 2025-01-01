import { TestBed } from '@angular/core/testing';

import { ElectricityProductionService } from './electricity-production.service';

describe('ElectricityProductionService', () => {
  let service: ElectricityProductionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElectricityProductionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
