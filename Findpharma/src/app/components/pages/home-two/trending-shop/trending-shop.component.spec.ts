import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrendingShopComponent } from './trending-shop.component';

describe('TrendingShopComponent', () => {
  let component: TrendingShopComponent;
  let fixture: ComponentFixture<TrendingShopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrendingShopComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TrendingShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
