import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopRightComponent } from './shop-right.component';

const routes: Routes = [{ path: '', component: ShopRightComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRightRoutingModule { }
