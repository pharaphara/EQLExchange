import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {HomeModule} from "./home/home.module";
import {RouterModule, Routes} from "@angular/router";
import {NavsideModule} from "./navside/navside.module";
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatSliderModule} from "@angular/material/slider";
import { DashboardComponent } from './dashboard/dashboard.component';
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatDividerModule} from "@angular/material/divider";
import { ChartComponent } from './chart/chart.component';
import {CommonModule} from "@angular/common";
import {WalletModule} from "./wallet/wallet.module";
import { ExplorerComponent } from './explorer/explorer.component';
import {MatTableModule} from "@angular/material/table";
import { TradeComponent } from './trade/trade.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ChartComponent,
    ExplorerComponent,
    TradeComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    WalletModule,
    NgbModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    HomeModule,
    NavsideModule,
    NoopAnimationsModule,
    MatSliderModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
