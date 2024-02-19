import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { CountUpModule } from 'ngx-countup';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RecaptchaModule, RecaptchaFormsModule } from 'ng-recaptcha';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { SharedModule } from '../../shared/shared.module';
import { BannerComponent } from './banner/banner.component';
import { ServicesComponent } from './services/services.component';
import { CounterComponent } from './counter/counter.component';
import { WhyUsComponent } from './why-us/why-us.component';
import { TeamComponent } from './team/team.component';
import { ServicesTwoComponent } from './services-two/services-two.component';
import { TestimonialsComponent } from './testimonials/testimonials.component';
import { CtaComponent } from './cta/cta.component';
import { QuoteComponent } from './quote/quote.component';
import { BlogsComponent } from './blogs/blogs.component';
import { TrendingShopComponent } from './trending-shop/trending-shop.component';
import { BestsellerShopComponent } from './bestseller-shop/bestseller-shop.component';
import { TopProductsComponent } from './top-products/top-products.component';


@NgModule({
  declarations: [
    HomeComponent,
    BannerComponent,
    ServicesComponent,
    CounterComponent,
    WhyUsComponent,
    TeamComponent,
    ServicesTwoComponent,
    TestimonialsComponent,
    CtaComponent,
    QuoteComponent,
    BlogsComponent,
    TrendingShopComponent,
    BestsellerShopComponent,
    TopProductsComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule,
    NgbModule,
    SlickCarouselModule,
    CountUpModule,
    ReactiveFormsModule,
    FormsModule,
    RecaptchaModule, 
    RecaptchaFormsModule
  ]
})
export class HomeModule { }
