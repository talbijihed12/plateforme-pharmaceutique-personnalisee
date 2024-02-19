import { NgModule } from "@angular/core";
import { BlogCreateComponent } from "./blog-create.component";
import { ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "../../shared/shared.module";
import { CommonModule } from "@angular/common";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NgxPaginationModule } from "ngx-pagination";
import { EditorModule } from "@tinymce/tinymce-angular";
import { CardModule } from 'primeng/card';
import { MessageService } from "primeng/api";


@NgModule({
    declarations: [
        BlogCreateComponent,
        
    ],
    imports: [
      SharedModule,
      ReactiveFormsModule,
      CommonModule,
      NgbModule,
      NgxPaginationModule,
      EditorModule,
      CardModule
    ],
    providers: [
      MessageService,
    ],
  
  })
  export class BlogCreateModule { }
  