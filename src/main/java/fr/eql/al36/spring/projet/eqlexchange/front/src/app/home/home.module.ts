import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HomeComponent} from "./component/home/home.component";
import {LoginComponent} from "./component/login/login.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "../app-routing.module";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "../dashboard/component/dashboard/dashboard.component";
import {DashboardModule} from "../dashboard/dashboard.module";

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: '', component: HomeComponent }
];

@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent
  ],
  exports: [
    HomeComponent,
    RouterModule,
  ],
  imports: [
    CommonModule,
    NgbModule,
    FormsModule,
    DashboardModule,
    RouterModule.forRoot(routes),
  ]
})
export class HomeModule { }
