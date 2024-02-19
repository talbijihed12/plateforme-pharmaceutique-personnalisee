import { NgModule } from "@angular/core";
import { SharedModule } from "src/app/components/shared/shared.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { NgbModalModule, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NgxPaginationModule } from "ngx-pagination";
import { NiceSelectModule } from "ng-nice-select";
import { ListeComponent } from "./liste.component";
import { RouterModule } from "@angular/router";
import { PipePipe } from "../pipes/pipe.pipe";
import { PipesComponent } from "../pipes/pipes.component";


@NgModule({
    declarations: [
        ListeComponent,
        PipesComponent,
        PipePipe
                
        
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
      NgbModalModule

      
      
    ],
  
  })
  export class ListeModule { }
  