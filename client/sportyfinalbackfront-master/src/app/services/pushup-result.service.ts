import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PushupResultService {
  private baseUrl = 'http://localhost:8090/api/pushups';

  constructor(private http: HttpClient) {}

  submitScore(userId: number, score: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/submit/${userId}`, { score });
  }

  getLeaderboard(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/leaderboard`);
  }
}
