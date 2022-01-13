import {Component, OnInit} from '@angular/core';
import {AuthenticateService} from "./home/service/authenticate.service";
import {interval, Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    './app.component.css'
  ]
})
export class AppComponent implements OnInit {

  title = 'ExchangeApp';
  data$: boolean = false
  constructor(private authenticate: AuthenticateService) {}

  ngOnInit(): void {
    this.authenticate.isAuthenticate().subscribe( (data) => {
      this.data$ = data
      console.log("helooooooooooooooooooooo" + data)
    });
  }



}
