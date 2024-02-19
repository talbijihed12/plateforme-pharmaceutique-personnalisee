import { TestBed } from '@angular/core/testing';

import { DoctorHelperService } from './doctor-helper.service';

describe('DoctorHelperService', () => {
  let service: DoctorHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoctorHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
