import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RecaptchaModule, RecaptchaFormsModule } from 'ng-recaptcha';

import { ContactRoutingModule } from './contact-routing.module';
import { ContactComponent } from './contact.component';
import { SharedModule } from '../../shared/shared.module';
import { ContentComponent } from './content/content.component';
import { ContactIconsComponent } from './contact-icons/contact-icons.component';
import { ContactMapComponent } from './contact-map/contact-map.component';


@NgModule({
  declarations: [
    ContactComponent,
    ContentComponent,
    ContactIconsComponent,
    ContactMapComponent
  ],
  imports: [
    CommonModule,
    ContactRoutingModule,
    SharedModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule,
    RecaptchaModule, 
    RecaptchaFormsModule
  ]
})
export class ContactModule { }
