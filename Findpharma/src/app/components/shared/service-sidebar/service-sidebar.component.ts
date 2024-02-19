import { Component } from '@angular/core';
import { ServiceHelperService } from '../../helper/service/service-helper.service';

@Component({
  selector: 'app-service-sidebar',
  templateUrl: './service-sidebar.component.html',
  styleUrls: ['./service-sidebar.component.css']
})
export class ServiceSidebarComponent extends ServiceHelperService {

}
