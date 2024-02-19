import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPageScrollCoreModule } from 'ngx-page-scroll-core';
import { NgxPageScrollModule } from 'ngx-page-scroll';

import { DoctorDetailsRoutingModule } from './doctor-details-routing.module';
import { DoctorDetailsComponent } from './doctor-details.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    DoctorDetailsComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    DoctorDetailsRoutingModule,
    SharedModule,
    NgbModule,
    NgxPageScrollCoreModule,
    NgxPageScrollModule
  ]
})
export class DoctorDetailsModule { }
