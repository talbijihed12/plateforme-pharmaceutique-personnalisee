import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicesImageComponent } from './services-image.component';

describe('ServicesImageComponent', () => {
  let component: ServicesImageComponent;
  let fixture: ComponentFixture<ServicesImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServicesImageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicesImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
