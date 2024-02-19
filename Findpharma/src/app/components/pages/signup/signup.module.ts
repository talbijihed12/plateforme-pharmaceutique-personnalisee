import { NgModule } from "@angular/core";
import { SignupComponent } from "./signup.component";
import { CommonModule } from "@angular/common";
import { SharedModule } from "../../shared/shared.module";
import { NgxPaginationModule } from "ngx-pagination";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { HeaderTwoComponent } from "../../shared/header-two/header-two.component";
import { BreadcrumbComponent } from "angular-crumbs";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        SignupComponent,
        
    ],
    imports: [
      SharedModule,
      ReactiveFormsModule,
      CommonModule,
      NgbModule,
      NgxPaginationModule
    ],
  
  })
  export class SignupModule { }
  