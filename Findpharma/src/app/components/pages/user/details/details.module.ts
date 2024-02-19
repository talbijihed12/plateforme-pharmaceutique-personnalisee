import { SharedModule } from "src/app/components/shared/shared.module";
import { DetailsComponent } from "./details.component";
import { ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NgxPaginationModule } from "ngx-pagination";
import { NgModule } from "@angular/core";

@NgModule({
    declarations: [
        DetailsComponent,
        
    ],
    imports: [
      SharedModule,
      ReactiveFormsModule,
      CommonModule,
      NgbModule,
      NgxPaginationModule
    ],
  
  })
  export class DetailsModule { }
  