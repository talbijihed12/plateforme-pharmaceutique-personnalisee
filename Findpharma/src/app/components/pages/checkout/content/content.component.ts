import { Component } from '@angular/core';
import { CartHelperService } from 'src/app/components/helper/shop/cart-helper.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent extends CartHelperService {
  public loginCollapsed = true;
  public couponCollapsed = true;
}
