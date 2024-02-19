import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AboutRoutingModule } from './about-routing.module';
import { AboutComponent } from './about.component';
import { SharedModule } from '../../shared/shared.module';
import { WorkProcessComponent } from './work-process/work-process.component';
import { WhyUsComponent } from './why-us/why-us.component';
import { CounterComponent } from './counter/counter.component';
import { TeamComponent } from './team/team.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CountUpModule } from 'ngx-countup';


@NgModule({
  declarations: [
    AboutComponent,
    WorkProcessComponent,
    WhyUsComponent,
    CounterComponent,
    TeamComponent
  ],
  imports: [
    CommonModule,
    AboutRoutingModule,
    SharedModule,
    NgbModule,
    CountUpModule
  ]
})
export class AboutModule { }
