import { Component } from '@angular/core';
import { WishlistHelperService } from 'src/app/components/helper/shop/wishlist-helper.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent extends WishlistHelperService {
}
