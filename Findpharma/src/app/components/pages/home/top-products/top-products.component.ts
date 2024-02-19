import { Component } from '@angular/core';
import { ShopService } from 'src/app/components/helper/shop/shop.service';

@Component({
  selector: 'app-top-products',
  templateUrl: './top-products.component.html',
  styleUrls: ['./top-products.component.css']
})
export class TopProductsComponent extends ShopService {

}
