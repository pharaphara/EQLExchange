import { Component, OnInit } from '@angular/core';
import {AuthenticateService} from "../service/authenticate.service";
import {User} from "../model/User";
import {Router} from "@angular/router";
import {Login} from "../model/login";
import {LoginResponse} from "../model/LogResponse";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  login: Login = new Login("","");
  err:number = 0;
  message :string | undefined ;

  constructor(private authService: AuthenticateService, private router:Router) {}

  ngOnInit(): void {

  }

  doLogin() {
    console.log(this.login.email);
    this.authService.authenticate(this.login).subscribe({
      next: (loginResp : LoginResponse)=>{
        console.log(loginResp);
        this.message = loginResp.message;
      },
      error: (err) => { console.log("error:"+err); this.message = "erreur appel WS login";}
    });
  }
}

