import { TestBed } from '@angular/core/testing';

import { ExchangeService } from './exchange.service';

describe('ExchangeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExchangeService = TestBed.get(ExchangeService);
    expect(service).toBeTruthy();
  });
});
