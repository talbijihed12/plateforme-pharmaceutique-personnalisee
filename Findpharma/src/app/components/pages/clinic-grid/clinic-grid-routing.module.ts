import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClinicGridComponent } from './clinic-grid.component';

const routes: Routes = [{ path: '', component: ClinicGridComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClinicGridRoutingModule { }
