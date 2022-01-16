import {HttpErrorResponse} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {AssetService} from '../../service/asset.service';
import {Asset} from '../../state/asset';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  public assets: Asset[] | undefined;

  constructor(private assetService: AssetService) { }

  public ngOnInit(): void {
    this.getAssets();
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
