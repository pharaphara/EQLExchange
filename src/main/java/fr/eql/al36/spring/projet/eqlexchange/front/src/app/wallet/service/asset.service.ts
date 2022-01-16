import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Asset} from "../state/asset";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  private apiServer = environment.apiWalletUrl;
  private email = 'alain.musque@yahoo.fr';

  constructor(private http: HttpClient) { }

  public getAssets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(`${this.apiServer}/userAssets?userEmail=${this.email}`)
  }
}
