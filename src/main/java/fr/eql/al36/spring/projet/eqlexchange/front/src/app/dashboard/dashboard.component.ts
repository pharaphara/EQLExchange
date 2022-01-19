import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {User} from "../model/User";
import {HttpErrorResponse} from "@angular/common/http";
import {Asset} from "../wallet/state/asset";
import {AssetService} from "../wallet/service/asset.service";
import {Currency} from "../explorer/state/currency";
import {CurrencyService} from "../explorer/service/currency.service";
import {Order} from "../transactions/state/order";
import {OrderService} from "../transactions/service/order.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  currencies!: Currency[];
  user!: User;
  lastOrder!: Order;
  assets!: Asset[];
  walletAmount: number = 0;

  constructor(private userService: UserService, private assetService: AssetService,
              private currencyService: CurrencyService, private orderService: OrderService) { }

  ngOnInit(): void {
    this.getUser();
    this.getAssets();
    this.getAllCurrency();
    this.getLastOrder();
  }

  public getUser(): void {
    this.userService.getCurrentUser().subscribe({
        next: (response: User) => {
          this.user = response;
        },
        error: (error: HttpErrorResponse) => {
          alert(error);
        }
      }
    )
  }

  public getLastOrder():void {
    this.orderService.getLastUserOrder().subscribe( {
        next: (response: Order) => {
        this.lastOrder = response;
      },
        error: (error: HttpErrorResponse) => {
          alert(error);
        }
    });
  }

  public getAssets(): void {
    this.assetService.getAssets().subscribe({
        next: (response: Asset[]) => {
          this.assets = response;
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        },
      }
    );
  }

  public getAllCurrency(): void {
    this.currencyService.getAllCurrencies().subscribe({
        next: (response: Currency[]) => {
          this.currencies = response || [];
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        },
      complete: () => this.getAmountWallet()
      }
    );
  }

  getCurrencyPrice(ticker: string): number {
    for(let currency of this.currencies) {
      if (currency.ticker == ticker) {
        console.log(currency.name)
        return currency.price;
      }
    }
    return 0;
  }

  public getAmountWallet(){

    for (let asset of this.assets) {
      if (asset.currencyTicker == 'EUR'){
        this.walletAmount += asset.amount;
      }
      this.walletAmount += this.getCurrencyPrice(asset.currencyTicker) * asset.amount;
    }
    return this.walletAmount;
  }

}
