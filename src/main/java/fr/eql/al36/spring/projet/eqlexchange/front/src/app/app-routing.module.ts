import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/component/home/home.component";
import {DashboardComponent} from "./dashboard/component/dashboard/dashboard.component";
import {CommonModule} from "@angular/common";
import {DashboardModule} from "./dashboard/dashboard.module";
import {HomeModule} from "./home/home.module";
import {AuthGuard} from "./guards/auth.guard";
import {WalletComponent} from "./wallet/component/wallet/wallet.component";

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: '', component: HomeComponent},
  { path: 'wallet', component: WalletComponent, canActivate: [AuthGuard] }
];

@NgModule({

  imports: [
    RouterModule.forRoot(routes),
    CommonModule,
    DashboardModule,
    HomeModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
