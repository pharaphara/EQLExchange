import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "../guards/auth.guard";
import {HomeComponent} from "../home/component/home/home.component";
import {WalletComponent} from "../wallet/component/wallet/wallet.component";

const routes: Routes = [

];

@NgModule({
  declarations: [
    DashboardComponent
  ],
  exports: [
    DashboardComponent,
    RouterModule
  ],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes),
  ]
})
export class DashboardModule { }
