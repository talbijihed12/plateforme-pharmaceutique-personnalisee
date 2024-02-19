import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';

import { DoctorGridRoutingModule } from './doctor-grid-routing.module';
import { DoctorGridComponent } from './doctor-grid.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    DoctorGridComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    DoctorGridRoutingModule,
    SharedModule,
    NgbModule,
    NgxPaginationModule
  ]
})
export class DoctorGridModule { }
