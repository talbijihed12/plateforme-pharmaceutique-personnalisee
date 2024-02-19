import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopLeftComponent } from './shop-left.component';

describe('ShopLeftComponent', () => {
  let component: ShopLeftComponent;
  let fixture: ComponentFixture<ShopLeftComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopLeftComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopLeftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
