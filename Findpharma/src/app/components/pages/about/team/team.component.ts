import { Component } from '@angular/core';
import { DoctorHelperService } from 'src/app/components/helper/doctor/doctor-helper.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent extends DoctorHelperService {

}
