import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root'
})
export class  LoginService {

  constructor(private oauthService:OAuthService) { }
  public login():void{
    this.oauthService.initImplicitFlowInternal();
  }
  public isLoggedIn():boolean{
    return (this.oauthService.hasValidIdToken() && this.oauthService.hasValidAccessToken());
  }
  public logout():void{
    this.oauthService.logOut();
  }
  public getUsername():string{
    const token=this.oauthService.getAccessToken();
    const payload=token.split('.')[1];
    const payloadDecodedJson=atob(payload);
    const payloadDecoded= JSON.parse(payloadDecodedJson);
    const preferredUsername = payloadDecoded.preferred_username
   return preferredUsername;
  }
  public getTOKEN():string{
    const token=this.oauthService.getAccessToken();


   return token;
  }
  public getUserId(): string {
    const token = this.oauthService.getAccessToken();
    const payload = token.split('.')[1];
    const payloadDecodedJson = atob(payload);
    const payloadDecoded = JSON.parse(payloadDecodedJson);
    const userId = payloadDecoded.sub; // Assuming the user ID is in the 'sub' claim
    return userId;
  }
  public getIsAdmin():boolean{
    const token=this.oauthService.getAccessToken();
    const payload=token.split('.')[1];
    const payloadDecodedJson=atob(payload);
    const payloadDecoded= JSON.parse(payloadDecodedJson);
    const preferredUsername = payloadDecoded.preferred_username
    console.log(payloadDecoded);
    console.log(preferredUsername)
    return payloadDecoded.realm_access.roles.indexOf('Admin','ROLE_ADMIN_FORUM','ROLE_ADMIN_FOYER','ROLE_ADMIN_RESTEAU') !==-1;

  }
  public getIsLoggedIn():boolean{

    return (this.oauthService.hasValidAccessToken() && this.oauthService.hasValidAccessToken());
  }
}
