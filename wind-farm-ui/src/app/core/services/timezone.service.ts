
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {urls} from "../../locator";

@Injectable({
    providedIn: 'root'
})
export class TimezoneService {

    private baseUrl = '';
    constructor(private http: HttpClient) {}

    getTimezones(): Observable<any> {
        const url = `${this.baseUrl}${urls.timezones}`;
        return this.http.get(url);
    }

}

