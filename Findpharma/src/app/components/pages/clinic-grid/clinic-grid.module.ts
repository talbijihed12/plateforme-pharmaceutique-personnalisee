import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';

import { ClinicGridRoutingModule } from './clinic-grid-routing.module';
import { ClinicGridComponent } from './clinic-grid.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    ClinicGridComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    ClinicGridRoutingModule,
    SharedModule,
    NgbModule,
    NgxPaginationModule
  ]
})
export class ClinicGridModule { }
