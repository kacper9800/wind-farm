import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {urls} from "../../locator";

@Injectable({
  providedIn: 'root'
})
export class ElectricityProductionService {

  private baseUrl = '';
  constructor(private http: HttpClient) {}

  getElectricityProduction(windFarmId: number, fromDate: Date, toDate: Date): Observable<any> {
    const url = `${this.baseUrl}${urls.electricityProduction}`;
    const params: HttpParams = new HttpParams()
      .set('windFarmId', windFarmId.toString())
      .set('fromDate', fromDate.toISOString())
      .set('toDate', toDate.toISOString());
    return this.http.get(url, { params });
  }
}
