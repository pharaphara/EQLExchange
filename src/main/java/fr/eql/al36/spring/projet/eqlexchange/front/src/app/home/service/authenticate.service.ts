import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of, tap} from "rxjs";
import {Login} from "../model/login";
import {LoginResponse} from "../model/LogResponse";

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  public currentRole : string = "?";
  private authenticateURL = 'http://localhost:8085/authenticate';
  private _headers = new HttpHeaders({'Content-Type': 'application/json'});
  public isAuthenticated: boolean = false;

  constructor(private http : HttpClient, private router: Router) { }

  public authenticate(login: Login): Observable<LoginResponse>{
    sessionStorage.setItem('authToken',"?");
    return this.http.post<LoginResponse>(this.authenticateURL,login, {headers: this._headers} )
      .pipe(
        tap((loginResponse)=>{
          this.saveToken(loginResponse);
          this.isAuthenticated = true;
          this.router.navigate(['eqlexchange']);
        }
        )
      );
  }

  public isAuthenticate(): Observable<boolean> {
    if(sessionStorage.getItem('authToken') != null) {
      return of(true);
    } else {
      return of(false);
    }
  }

  public logout() {
    sessionStorage.clear();
    this.isAuthenticated = false;
    this.isAuthenticate();
    this.router.navigate(['']);
  }

  private saveToken(loginResponse:LoginResponse){
    if(loginResponse.ok){
      this.currentRole = "ROLE_USER";
      sessionStorage.setItem('authToken',loginResponse.token);
    }
    else{
      sessionStorage.setItem('authToken',"");
      this.currentRole = "?";
    }
  }

}
