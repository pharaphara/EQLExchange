import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WalletComponent} from "./component/wallet/wallet.component";
import {BrowserModule} from "@angular/platform-browser";



@NgModule({
  declarations: [
    WalletComponent
  ],
  imports: [
    CommonModule,
    BrowserModule
  ]
})
export class WalletModule { }
