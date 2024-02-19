import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';

import { DoctorListRoutingModule } from './doctor-list-routing.module';
import { DoctorListComponent } from './doctor-list.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    DoctorListComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    DoctorListRoutingModule,
    SharedModule,
    NgbModule,
    NgxPaginationModule
  ]
})
export class DoctorListModule { }
