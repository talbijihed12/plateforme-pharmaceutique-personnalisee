import { Component } from '@angular/core';
import { CartHelperService } from '../../helper/shop/cart-helper.service';

@Component({
  selector: 'app-small-cart',
  templateUrl: './small-cart.component.html',
  styleUrls: ['./small-cart.component.css']
})
export class SmallCartComponent extends CartHelperService {
}
