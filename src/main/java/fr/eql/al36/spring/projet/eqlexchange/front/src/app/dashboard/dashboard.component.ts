import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {User} from "../model/User";
import {HttpErrorResponse} from "@angular/common/http";
import {Asset} from "../wallet/state/asset";
import {AssetService} from "../wallet/service/asset.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  user!: User;
  assets!: Asset[];
  walletAmount: number = 0;

  constructor(private userService: UserService, private assetService: AssetService) {
  }

  ngOnInit(): void {
    this.getUser();
    this.getAssets();
  }

  public getUser(): void {
    this.userService.getCurrentUser().subscribe(
      {
        next: (response: User) => {
          this.user = response;
        },
        error: (error: HttpErrorResponse) => {
          alert(error);
        }
      }
    )
  }

  public getAssets(): void {
    this.assetService.getAssets().subscribe({
        next: (response: Asset[]) => {
          this.assets = response;
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        },
      complete: () => this.getAmountWallet()
      }
    );
  }

  public getAmountWallet(){
   this.assets?.forEach(
     element => {
       this.walletAmount += element.amount
     },
   );
  }

}
