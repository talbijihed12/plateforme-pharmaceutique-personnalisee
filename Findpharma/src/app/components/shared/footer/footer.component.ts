import { Component } from '@angular/core';
import { ServiceHelperService } from '../../helper/service/service-helper.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent extends ServiceHelperService {
}
