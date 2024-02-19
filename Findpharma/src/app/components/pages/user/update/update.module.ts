import { NgModule } from "@angular/core";
import { UpdateComponent } from "./update.component";
import { SharedModule } from "src/app/components/shared/shared.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NiceSelectModule } from "ng-nice-select";
import { NgxPaginationModule } from "ngx-pagination";
import { RouterModule } from "@angular/router";

@NgModule({
    declarations: [
        UpdateComponent,
        
                
        
    ],
    imports: [
      SharedModule,
      ReactiveFormsModule,
      CommonModule,
      NgbModule,
      NiceSelectModule,
      FormsModule,
      NgxPaginationModule,
      RouterModule,

      
      
    ],
  
  })
  export class UpdateModule { }
  