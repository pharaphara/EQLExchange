import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {TradeOrder} from "../trade-order";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TradeOrderService {

  private apiUrl = environment.apiMatchingEngine

  constructor(private http: HttpClient) { }

  public addTradeOrder(tradeOrder: TradeOrder): Observable<TradeOrder> {
    return this.http.post<TradeOrder>(`${this.apiUrl}/order`, tradeOrder);
  }
}
