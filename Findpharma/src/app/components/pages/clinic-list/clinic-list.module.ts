import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';

import { ClinicListRoutingModule } from './clinic-list-routing.module';
import { ClinicListComponent } from './clinic-list.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    ClinicListComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    ClinicListRoutingModule,
    SharedModule,
    NgbModule,
    NgxPaginationModule
  ]
})
export class ClinicListModule { }
