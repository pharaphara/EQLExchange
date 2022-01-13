import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "../guards/auth.guard";
import {HomeComponent} from "../home/component/home/home.component";
import {WalletComponent} from "../wallet/component/wallet/wallet.component";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatSidenavModule} from "@angular/material/sidenav";

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'eqlexchange', component: DashboardComponent, canActivate: [AuthGuard] , children: [
      { path: 'wallet', component: WalletComponent, canActivate: [AuthGuard] },
      { path: 'eqlexchange', component: DashboardComponent, canActivate: [AuthGuard] }
] }
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
    MatListModule,
    MatIconModule,
    MatSidenavModule,
  ]
})
export class DashboardModule { }
