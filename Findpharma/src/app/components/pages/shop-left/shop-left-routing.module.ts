import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopLeftComponent } from './shop-left.component';

const routes: Routes = [{ path: '', component: ShopLeftComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopLeftRoutingModule { }
