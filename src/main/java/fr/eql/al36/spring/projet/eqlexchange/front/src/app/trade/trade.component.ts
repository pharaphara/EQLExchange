import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {TradeOrder} from "./state/trade-order";
import {TradeModule} from "./trade.module";
import {TradeOrderService} from "./state/service/trade-order.service";
import {HttpErrorResponse} from "@angular/common/http";

const currency = {

}

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.scss']
})
export class TradeComponent implements OnInit {

  public tradeOrder!: TradeOrder;
  public form!: FormGroup;
  public currencyId!: number;
  typeControl = new FormControl('BID');

  constructor(private route: ActivatedRoute, private tradeOrderService: TradeOrderService) {

  }

  ngOnInit(): void {
    this.form = new FormGroup( {
      user: new FormControl(sessionStorage.getItem('email')),
      currencyPair: new FormControl('BTC_EUR'),
      orderType: this.typeControl,
      amount: new FormControl(),
      limitPrice: new FormControl()

    })
    let id = parseInt(<string>this.route.snapshot.paramMap.get('id'));
    this.currencyId = id;
  }

  sendTradeOrder() {
    console.log(this.form.value);
    this.tradeOrderService.addTradeOrder(this.form.value).subscribe(

      (response: TradeOrder) => {
        console.log(response)
      },
      (error: HttpErrorResponse) => {
        alert(error)
      }
    )

  }

}
