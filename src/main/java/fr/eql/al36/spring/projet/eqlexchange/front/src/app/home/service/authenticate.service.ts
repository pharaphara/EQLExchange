import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {Login} from "../model/login";
import {LoginResponse} from "../model/LogResponse";

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  public currentRole : string = "?";
  private apiURL: string = 'http://localhost:8085/hello';
  private authenticateURL = 'http://localhost:8085/authenticate';
  private _headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http : HttpClient, private router: Router) { }

  public authenticate(login: Login): Observable<LoginResponse>{

    sessionStorage.setItem('authToken',"?");
    return this.http.post<LoginResponse>(this.authenticateURL,login, {headers: this._headers} )
      .pipe(
        tap((loginResponse)=>{
          this.saveToken(loginResponse);
          this.router.navigate(['dashboard']);
        }
        )
      );
  }

  private saveToken(loginResponse:LoginResponse){
    if(loginResponse.ok){
      this.currentRole = "ROLE_USER"; //or ...
      sessionStorage.setItem('authToken',loginResponse.token);
    }
    else{
      sessionStorage.setItem('authToken',"");
      this.currentRole = "?";
    }
  }

}
