import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { LoginService } from './login.service';
import { Observable } from 'rxjs';
import { User } from 'src/app/components/models/User';
import { SignupRequestPayload } from 'src/app/components/models/SignupRequestPayload';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiServiceUrl=environment.apiBaseUrl;
  httpClient: any;
  httpOption={headers:new HttpHeaders({'Content-type':'application/json'})}

  constructor(private http:HttpClient,private loginService:LoginService) { }
  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    console.log(signupRequestPayload)
    return this.http.post<any>(`${this.apiServiceUrl}/keycloakUser/signup`, signupRequestPayload);
  }
  public list():Observable<User[]>{
    return this.http.get<User[]>(`${this.apiServiceUrl}/keycloakUser/admin/findAll`, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
  public detail(userName:string):Observable<User>{
    return this.http.get<User>(`${this.apiServiceUrl}/keycloakUser/admin/details/${userName}`, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
  public create(user:User):Observable<any>{
    return this.http.post<any>(`${this.apiServiceUrl}/keycloakUser/addUserByAdmin`,user, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
  public update(id:number,user:User):Observable<any>{
    return this.http.put<any>(`${this.apiServiceUrl}/keycloakUser/admin/UpdateUserByAdmin/${id}`,user, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
  public delete(id:number):Observable<any>{
    return this.http.delete<any>(`${this.apiServiceUrl}/keycloakUser/deleteAccount/${id}`, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
  public deleteMyAccount():Observable<any>{
    return this.http.delete<any>(`${this.apiServiceUrl}/keycloakUser/deleteMyAccount`, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
  public updateprofile(user:User):Observable<any>{
    return this.http.put<any>(`${this.apiServiceUrl}/keycloakUser/UpdateUser`,user, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
  assignRoleToUser(id: string, roleName: string): Observable<any> {
    return this.http.put<any>(`${this.apiServiceUrl}/keycloakUser/admin/roles/${id}?roleName=${roleName}`, {
      headers: {
        'Authorization': `Bearer ${this.loginService.getTOKEN()}`,
        'Content-type':'application/json'

      }
    })
  }
}
