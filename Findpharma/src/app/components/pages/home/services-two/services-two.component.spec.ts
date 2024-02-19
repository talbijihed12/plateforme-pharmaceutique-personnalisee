import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicesTwoComponent } from './services-two.component';

describe('ServicesTwoComponent', () => {
  let component: ServicesTwoComponent;
  let fixture: ComponentFixture<ServicesTwoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServicesTwoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicesTwoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
