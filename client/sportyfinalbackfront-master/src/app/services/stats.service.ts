import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Stats } from '../models/stats';


@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private apiUrl = 'http://localhost:8090/rest/stat'; // Assure-toi que l'URL correspond à ton backend

  constructor(private http: HttpClient) {}

  addStats(stats: Stats, userId: number): Observable<Stats> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  
    return this.http.post<Stats>(`${this.apiUrl}/statistics/${userId}`, stats, { headers });
  }
}
