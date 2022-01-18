import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {User} from "../model/User";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  user!: User;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.getUser();

  }

  public getUser(): void {
    this.userService.getCurrentUser().subscribe(
      {
        next: (response: User) => {
          this.user = response;
          console.log(this.user.walletAddress   + 'USERRR')
        },
        error: (error: HttpErrorResponse) => {
          alert(error);
        }
      }
    )
  }

}
