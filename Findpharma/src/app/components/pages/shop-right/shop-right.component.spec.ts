import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopRightComponent } from './shop-right.component';

describe('ShopRightComponent', () => {
  let component: ShopRightComponent;
  let fixture: ComponentFixture<ShopRightComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopRightComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopRightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
