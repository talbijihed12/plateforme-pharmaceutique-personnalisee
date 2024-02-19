import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPageScrollCoreModule } from 'ngx-page-scroll-core';
import { NgxPageScrollModule } from 'ngx-page-scroll';

import { ClinicDetailsRoutingModule } from './clinic-details-routing.module';
import { ClinicDetailsComponent } from './clinic-details.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    ClinicDetailsComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    ClinicDetailsRoutingModule,
    SharedModule,
    NgbModule,
    NgxPageScrollCoreModule,
    NgxPageScrollModule
  ]
})
export class ClinicDetailsModule { }
