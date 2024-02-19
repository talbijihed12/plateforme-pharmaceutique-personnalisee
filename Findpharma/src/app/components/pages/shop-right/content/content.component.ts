import { Component } from '@angular/core';
import { ShopService } from 'src/app/components/helper/shop/shop.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent extends ShopService {

}