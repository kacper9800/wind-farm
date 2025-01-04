import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {urls} from "../../locator";

@Injectable({
    providedIn: 'root'
})
export class ElectricityProductionService {

    private baseUrl = '';

    constructor(private http: HttpClient) {
    }

    getElectricityProduction(windFarmId: number, timezone: string, fromDate: Date | null, toDate: Date | null, pageSize: number, pageNumber: number): Observable<any> {
        const url = `${this.baseUrl}${urls.electricityProduction}`;
        let params = new HttpParams().set('windFarmId', windFarmId.toString());
        params = params.set('timezone', timezone);
        if (fromDate) {
            params = params.set('fromDate', fromDate.toISOString());
        }
        if (toDate) {
            params = params.set('toDate', toDate.toISOString());
        }
        params = params.set('pageSize', pageSize.toString());
        params = params.set('pageNumber', pageNumber.toString());


        return this.http.get<number>(url, {params});
    }

    getAverageCapacityFactor(windFarmId: number, fromDate: Date | null, toDate: Date | null): Observable<number> {
        const url = `${this.baseUrl}${urls.electricityProductionAverage}`;
        let params = new HttpParams().set('windFarmId', windFarmId.toString());
        if (fromDate) {
            params = params.set('fromDate', fromDate.toISOString());
        }
        if (toDate) {
            params = params.set('toDate', toDate.toISOString());
        }

        return this.http.get<number>(url, {params});
    }
}
