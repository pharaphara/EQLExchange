import { Component, OnInit } from '@angular/core';
import {Asset} from "../../state/asset";
import {AssetService} from "../../service/asset.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  public assets: Asset[] | undefined;

  constructor(private assetService: AssetService) { }

  ngOnInit(): void {
    this.getAssets();
  }

  public getAssets(): void {
    this.assetService.getAssets().subscribe(
      (response: Asset[]) => {
        this.assets = response || [];
        console.log(this.assets)
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

}
