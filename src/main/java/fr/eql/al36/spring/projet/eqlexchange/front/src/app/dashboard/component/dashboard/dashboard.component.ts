import { Component, OnInit } from '@angular/core';
import {AuthenticateService} from "../../../home/service/authenticate.service";

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private authenticateService: AuthenticateService ) { }

  ngOnInit(): void { }

  logout() {
    console.log('hello world' + sessionStorage.getItem('authToken'))
    this.authenticateService.logout();
  }

}
