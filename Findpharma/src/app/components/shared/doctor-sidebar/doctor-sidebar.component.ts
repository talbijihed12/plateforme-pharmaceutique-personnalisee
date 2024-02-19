import { Component } from '@angular/core';
import { DoctorHelperService } from '../../helper/doctor/doctor-helper.service';
import { Options, LabelType } from "@angular-slider/ngx-slider";

@Component({
  selector: 'app-doctor-sidebar',
  templateUrl: './doctor-sidebar.component.html',
  styleUrls: ['./doctor-sidebar.component.css']
})
export class DoctorSidebarComponent extends DoctorHelperService {
  minValue: number = 0;
  maxValue: number = 50;
  options: Options = {
    floor: 0,
    ceil: 50
  };
}
