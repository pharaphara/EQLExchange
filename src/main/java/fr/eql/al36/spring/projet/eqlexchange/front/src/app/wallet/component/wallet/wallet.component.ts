import {HttpErrorResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {AssetService} from '../../service/asset.service';
import {Asset} from '../../state/asset';
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/User";
import {LoginResponse} from "../../../home/model/LogResponse";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  public assets!: Asset[];
  public user!: User;
  amount!: number;
  displayedColumns: string[] = ['Logo', 'Currency', 'Units'];

  constructor(private assetService: AssetService, private userService: UserService) {
  }

  public ngOnInit(): void {
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
    this.assetService.getAssets().subscribe(
      {
        next: (response: Asset[]) => {
          this.assets = response || [];
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        },
        complete: () => this.getAmount()
      }
    );
  }

  public getAmount(): void {
    this.assetService.getWalletAmount(this.assets).subscribe( {
     next: (response:number) => {
       this.amount = response;
     }
    });
  }

}
