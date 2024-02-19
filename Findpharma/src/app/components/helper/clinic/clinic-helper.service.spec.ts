import { TestBed } from '@angular/core/testing';

import { ClinicHelperService } from './clinic-helper.service';

describe('ClinicHelperService', () => {
  let service: ClinicHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClinicHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
