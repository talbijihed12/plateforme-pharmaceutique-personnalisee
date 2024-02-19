import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClinicGridComponent } from './clinic-grid.component';

describe('ClinicGridComponent', () => {
  let component: ClinicGridComponent;
  let fixture: ComponentFixture<ClinicGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClinicGridComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClinicGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
