import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";
import {TradeOrderService} from "./service/trade-order.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.scss']
})
export class TradeComponent implements OnInit {

  public isTransfer: boolean = false;
  public form!: FormGroup;
  public currencyId!: string;
  public pair!: string;
  typeControl = new FormControl('BID');

  constructor(private route: ActivatedRoute, private tradeOrderService: TradeOrderService) {

  }

  ngOnInit(): void {
    this.isTransfer = false;
    this.currencyId = <string>this.route.snapshot.paramMap.get('id');
    this.pair = this.currencyId + '_EUR';
    this.form = new FormGroup({
      user: new FormControl(sessionStorage.getItem('email')),
      currencyPair: new FormControl(this.pair),
      orderType: this.typeControl,
      amount: new FormControl(),
      limitPrice: new FormControl()
    });
  }

  sendTradeOrder() {
    this.tradeOrderService.addTradeOrder(this.form.value).subscribe({
        next: () => {
          this.isTransfer = true;
        },
        error: (error: HttpErrorResponse) => {
          alert(error)
        }
      }
    )
  }

}
