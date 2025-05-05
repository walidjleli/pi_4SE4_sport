import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Stats } from '../models/stats';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private baseUrl = 'http://localhost:8090/rest/stat';

  constructor(private http: HttpClient) {}

  addStats(stats: Stats, userId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/add/${userId}`, stats);
  }

  getStatsByUserId(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/${userId}`);
  }

  updateStats(id: number, stats: Stats): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, stats);
  }
}
