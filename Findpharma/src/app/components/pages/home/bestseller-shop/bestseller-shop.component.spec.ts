import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BestsellerShopComponent } from './bestseller-shop.component';

describe('BestsellerShopComponent', () => {
  let component: BestsellerShopComponent;
  let fixture: ComponentFixture<BestsellerShopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BestsellerShopComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BestsellerShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
