import { TestBed } from '@angular/core/testing';

import { EmprendimientosService } from './emprendimientos.service';

describe('EmprendimientosService', () => {
  let service: EmprendimientosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmprendimientosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
