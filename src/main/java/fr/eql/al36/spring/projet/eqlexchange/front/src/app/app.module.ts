import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {HomeModule} from "./home/home.module";
import {RouterModule, Routes} from "@angular/router";
import {DashboardModule} from "./dashboard/dashboard.module";
import { WalletComponent } from './wallet/component/wallet/wallet.component';


@NgModule({
  declarations: [
    AppComponent,
    WalletComponent,
  ],
  imports: [
    BrowserModule,
    NgbModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    HomeModule,
    DashboardModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
