import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';

import { ShopGridRoutingModule } from './shop-grid-routing.module';
import { ShopGridComponent } from './shop-grid.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    ShopGridComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    ShopGridRoutingModule,
    SharedModule,
    NgbModule,
    NgxPaginationModule
  ]
})
export class ShopGridModule { }
