import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {urls} from "../../locator";

@Injectable({
  providedIn: 'root'
})
export class WindFarmService {

  private baseUrl = '';
  constructor(private http: HttpClient) {}

  getWindFarms(): Observable<any> {
    const url = `${this.baseUrl}${urls.windFarm}`;
    return this.http.get(url);
  }

  // Get a specific wind farm by ID
  getWindFarmById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  // Create a new wind farm
  createWindFarm(data: any): Observable<any> {
    const url = `${this.baseUrl}${urls.windFarm}`;
    return this.http.post<any>(url, data);
  }

  // Update an existing wind farm
  updateWindFarm(id: string, data: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, data);
  }

  // Delete a wind farm
  deleteWindFarm(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }
}

