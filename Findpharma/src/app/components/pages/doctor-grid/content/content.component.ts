import { Component } from '@angular/core';
import { DoctorHelperService } from 'src/app/components/helper/doctor/doctor-helper.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent extends DoctorHelperService {

}
