import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopGridComponent } from './shop-grid.component';

const routes: Routes = [{ path: '', component: ShopGridComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopGridRoutingModule { }
