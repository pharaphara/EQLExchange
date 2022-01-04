import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { LoginComponent } from './login/login.component';
import {EqlApiService} from "./eql-api.service";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule
  ],
  providers: [EqlApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
