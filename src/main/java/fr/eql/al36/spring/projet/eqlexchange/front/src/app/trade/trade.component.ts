import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.scss']
})
export class TradeComponent implements OnInit {

  public currencyId!: number;
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    let id = parseInt(<string>this.route.snapshot.paramMap.get('id'));
    this.currencyId = id;
  }

}
