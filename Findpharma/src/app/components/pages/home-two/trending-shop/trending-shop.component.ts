import { Component } from '@angular/core';
import { ShopService } from 'src/app/components/helper/shop/shop.service';

@Component({
  selector: 'app-trending-shop',
  templateUrl: './trending-shop.component.html',
  styleUrls: ['./trending-shop.component.css']
})
export class TrendingShopComponent extends ShopService {
  settings = {
    slidesToShow: 4,
    slidesToScroll: 1,
    arrows: false,
    dots: true,
    autoplay: true,
    centerMode: true,
    centerPadding: 0,
    responsive: [
    {
        breakpoint: 991,
        settings: {
          slidesToShow: 3
        }
      },
      {
        breakpoint: 767,
        settings: {
          slidesToShow: 2
        }
      },
      {
        breakpoint: 576,
        settings: {
          slidesToShow: 1
        }
      }
    ]
  }
}