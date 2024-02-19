import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { BreadcrumbService, Breadcrumb } from 'angular-crumbs';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { LoginService } from './services/userService/login.service';
import { AuthConfig, NullValidationHandler, OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [
    Location, {
      provide: LocationStrategy,
      useClass: PathLocationStrategy
    }
  ]
})
export class AppComponent implements OnInit {
  isLoggedIn!:boolean;
  isAdmin!:boolean;
  username!:string;
  constructor(private titleService: Title, private breadcrumbService: BreadcrumbService,private oauthService:OAuthService,private loginService:LoginService) {
  }
  authFlowConfig: AuthConfig = {
    issuer: 'http://keycloak:8180/auth/realms/plateforme-pharmaceutique',
    requireHttps: false,
    redirectUri: window.location.origin,
    clientId: 'angular-client',
    responseType: 'code',
    scope: 'openid profile email offline_access',

    showDebugInformation: true,
  };
  configure():void{
    this.oauthService.configure(this.authFlowConfig);
    this.oauthService.tokenValidationHandler=new NullValidationHandler;
    this.oauthService.setupAutomaticSilentRefresh();
    this.oauthService.loadDiscoveryDocument().then(()=>this.oauthService.tryLogin())
    .then (()=>{
      if(this.oauthService.getIdentityClaims()){
        this.isLoggedIn=this.loginService.getIsLoggedIn();
        this.isAdmin=this.loginService.getIsAdmin();
        this.username=this.loginService.getUsername();

       // this.preferredUsername;
       // this.username=this.oauthService.()[`preferred_username`];
      }
    });
  }


  
  ngOnInit(): void {
    this.breadcrumbService.breadcrumbChanged.subscribe(crumbs => {
      this.titleService.setTitle(this.createTitle(crumbs));
    });
  }
  onActivate(_event:any){
    window.scroll(0,0);
  }
  private createTitle(routesCollection: Breadcrumb[]) {
    const title = "Find-Pharma - Doctors Appointment Booking - Angular Template";
    const titles = routesCollection.filter((route) => route.displayName);

    if (!titles.length) { return title; }

    const routeTitle = this.titlesToString(titles);
    return `${title}${routeTitle}`;
  }

  private titlesToString(titles: any[]) {
    return titles.reduce((prev, curr) => {
      return `${prev} | ${curr.displayName}`;
    }, '');
  }
}
