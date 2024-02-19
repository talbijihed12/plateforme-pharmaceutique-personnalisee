import { BrowserModule } from '@angular/platform-browser';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BreadcrumbComponent, BreadcrumbModule } from 'angular-crumbs';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OAuthModule } from 'angular-oauth2-oidc';
import { NgxPaginationModule, PaginatePipe } from 'ngx-pagination';
import { ToastrModule } from 'ngx-toastr';
import { SignupModule } from './components/pages/signup/signup.module';
import { SignupComponent } from './components/pages/signup/signup.component';
import { DetailsComponent } from './components/pages/user/details/details.component';
import { UpdateComponent } from './components/pages/user/update/update.component';
import { ListeComponent } from './components/pages/user/liste/liste.component';
import { PipesComponent } from './components/pages/user/pipes/pipes.component';
import { UpdateByAdminComponent } from './components/pages/user/update-by-admin/update-by-admin.component';
import { PipePipe } from './components/pages/user/pipes/pipe.pipe';
import { ListeModule } from './components/pages/user/liste/liste.module';
import { DetailsModule } from './components/pages/user/details/details.module';
import { UpdateByAdminModule } from './components/pages/user/update-by-admin/update-by-admin.module';
import { UpdateModule } from './components/pages/user/update/update.module';
import { BlogCreateModule } from './components/pages/blog-create/blog-create.module';

@NgModule({
  declarations: [
    AppComponent,
    
    
    
    
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    BreadcrumbModule,
    NgbModule,
    ListeModule,
    UpdateByAdminModule,
    UpdateModule,
    DetailsModule,
    BlogCreateModule,
    HttpClientModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: ['http://localhost:8098'],
          sendAccessToken: true
      }
    })

  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
