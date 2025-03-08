import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Stats } from '../models/stats';

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private apiUrl = 'http://localhost:8090/rest/stat';

  constructor(private http: HttpClient) {}

  private getHttpOptions() {
    const token = localStorage.getItem('token');
    if (!token) {
      console.warn("⚠️ Aucun token trouvé, l'utilisateur n'est peut-être pas connecté !");
      return { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }; // 
    }
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };
  }
  

  addStats(stats: Stats, userId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/add/${userId}`, stats, this.getHttpOptions());
  }
}
