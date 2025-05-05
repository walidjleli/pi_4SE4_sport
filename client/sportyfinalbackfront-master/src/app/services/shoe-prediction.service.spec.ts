import { TestBed } from '@angular/core/testing';

import { ShoePredictionService } from './shoe-prediction.service';

describe('ShoePredictionService', () => {
  let service: ShoePredictionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShoePredictionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
