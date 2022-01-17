import {OrderType} from "../../model/enum/order-type";
import {TradingPair} from "../../model/enum/trading-pair";

export interface TradeOrder {
  email: string;
  pair: TradingPair;
  type: OrderType;
  amount: number;
  limit: number;
}
