import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WalletComponent} from "./component/wallet/wallet.component";
import {BrowserModule} from "@angular/platform-browser";
import { MatTableModule } from '@angular/material/table';



@NgModule({
  declarations: [
    WalletComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatTableModule
  ]
})
export class WalletModule { }
