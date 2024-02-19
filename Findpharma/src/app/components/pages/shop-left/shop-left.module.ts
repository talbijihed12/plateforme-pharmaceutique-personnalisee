import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';

import { ShopLeftRoutingModule } from './shop-left-routing.module';
import { ShopLeftComponent } from './shop-left.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    ShopLeftComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    ShopLeftRoutingModule,
    SharedModule,
    NgbModule,
    NgxPaginationModule
  ]
})
export class ShopLeftModule { }
