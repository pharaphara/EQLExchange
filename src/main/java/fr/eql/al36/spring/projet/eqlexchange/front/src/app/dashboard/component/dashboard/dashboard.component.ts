import { Component, OnInit } from '@angular/core';
import {AuthenticateService} from "../../../home/service/authenticate.service";

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private authenticateService: AuthenticateService ) { }

  ngOnInit(): void {
  }

  logout() {
    this.authenticateService.logout();
  }

}
