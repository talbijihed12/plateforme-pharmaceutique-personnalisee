import { Component } from '@angular/core';
import { ShopService } from 'src/app/components/helper/shop/shop.service';

@Component({
  selector: 'app-trending-shop',
  templateUrl: './trending-shop.component.html',
  styleUrls: ['./trending-shop.component.css']
})
export class TrendingShopComponent extends ShopService {

}
