import { Component, Input } from '@angular/core';
import { HelperService } from '../../helper/helper.service';
import { AuthConfig, NullValidationHandler, OAuthService } from 'angular-oauth2-oidc';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-header-two',
  templateUrl: './header-two.component.html',
  styleUrls: ['./header-two.component.css']
})
export class HeaderTwoComponent extends HelperService {
  username!:string;
  isLoggedIn!:boolean;
  isAdmin!:boolean;
  @Input() isLogged!:boolean;
  @Input() isAdminn!:boolean;
  constructor(private oauthService:OAuthService,private router:Router,private userService:UserService) {
    super();
    this.configure();
  }

  authFlowConfig: AuthConfig = {
    issuer: 'http://keycloak:8180/auth/realms/plateforme-pharmaceutique',
    requireHttps: false,
    redirectUri: window.location.origin,
    clientId: 'angular-client',
    responseType: 'code',
    scope: 'openid profile email offline_access',

    showDebugInformation: true,
  }
  configure():void{
    this.oauthService.configure(this.authFlowConfig);
    this.oauthService.tokenValidationHandler=new NullValidationHandler;
    this.oauthService.setupAutomaticSilentRefresh();
    this.oauthService.loadDiscoveryDocument().then(()=>this.oauthService.tryLogin())
    .then (()=>{
      if(this.oauthService.getIdentityClaims()){
        this.isLoggedIn=this.getIsLoggedIn();
        this.isAdmin=this.getIsAdmin();
        this.username=this.getUsername();

       // this.preferredUsername;
       // this.username=this.oauthService.()[`preferred_username`];
      }
    });

  }
  login():void{

    this.oauthService.initImplicitFlowInternal();
  }
  public getIsLoggedIn():boolean{

    return (this.oauthService.hasValidAccessToken() && this.oauthService.hasValidAccessToken());
  }
  logout():void{
    this.oauthService.logOut();

  }
  onDelete():void{
    if (confirm('this will delete your profile?')) {
      if (confirm('Are you sure you want to delete this profile?')){
      this.userService.deleteMyAccount().subscribe(
        data => {
          console.log(data);
          alert("User Deleted Successfully");
          this.router.navigate(['']); // Redirect to home page
          this.logout(); // Call the logout function
        },
        error => console.log(error)
      );
    }
  }
  this.logout();
  this.router.navigate(['']);
  }

  public getIsAdmin():boolean{
    const token=this.oauthService.getAccessToken();
    const payload=token.split('.')[1];
    const payloadDecodedJson=atob(payload);
    const payloadDecoded= JSON.parse(payloadDecodedJson);
    const preferredUsername = payloadDecoded.preferred_username
    console.log(payloadDecoded);
    console.log(preferredUsername)
    return payloadDecoded.realm_access.roles.indexOf('Admin') !==-1;

  }
  public getUsername():string{
    const token=this.oauthService.getAccessToken();
    const payload=token.split('.')[1];
    const payloadDecodedJson=atob(payload);
    const payloadDecoded= JSON.parse(payloadDecodedJson);
    const preferredUsername = payloadDecoded.preferred_username
   return preferredUsername;
  }

}
