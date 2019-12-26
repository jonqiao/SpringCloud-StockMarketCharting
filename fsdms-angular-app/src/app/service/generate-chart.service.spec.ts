import { TestBed } from '@angular/core/testing';

import { GenerateChartService } from './generate-chart.service';

describe('GenerateChartService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GenerateChartService = TestBed.get(GenerateChartService);
    expect(service).toBeTruthy();
  });
});
