import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {HomeModule} from "./home/home.module";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "./dashboard/component/dashboard/dashboard.component";
import {HomeComponent} from "./home/component/home/home.component";
import {DashboardModule} from "./dashboard/dashboard.module";

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: '', component: HomeComponent }
];

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    NgbModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    HomeModule,
    DashboardModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
