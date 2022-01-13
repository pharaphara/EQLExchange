import { Component, OnInit } from '@angular/core';
import {AuthenticateService} from "../../../home/service/authenticate.service";
import {MatDialog} from "@angular/material/dialog";
import {LogoutComponent} from "../logout/logout.component";

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void { }

  openDialog(): void {
    const dialogRef = this.dialog.open(LogoutComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
