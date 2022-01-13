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
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatSliderModule} from "@angular/material/slider";


@NgModule({
  declarations: [
    AppComponent
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
    NoopAnimationsModule,
    MatSliderModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
