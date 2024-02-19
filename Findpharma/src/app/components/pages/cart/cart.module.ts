import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { CartRoutingModule } from './cart-routing.module';
import { CartComponent } from './cart.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';


@NgModule({
  declarations: [
    CartComponent,
    ContentComponent
  ],
  imports: [
    CommonModule,
    CartRoutingModule,
    SharedModule,
    NgbModule,
    FormsModule
  ]
})
export class CartModule { }
