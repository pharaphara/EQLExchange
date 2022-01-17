import {HttpErrorResponse} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
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

  public assets: Asset[] | undefined;
  public user!: User;

  constructor(private assetService: AssetService, private userService: UserService) { }

  public ngOnInit(): void {
    this.getUser();
    this.getAssets();
  }

  public getUser():void {
    this.userService.getCurrentUser().subscribe(
      (response: User) => {
        this.user = response;
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    )
  }

  public getAssets(): void {
    this.assetService.getAssets().subscribe(
      (response: Asset[]) => {
        this.assets = response || [];
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      },
    );
  }

}
