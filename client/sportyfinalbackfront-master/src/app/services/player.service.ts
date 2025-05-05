// src/app/services/player.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private apiUrl = 'http://localhost:8090/rest/auth/users/players'; // ✅ URL pour récupérer les joueurs

  constructor(private http: HttpClient) {}

  getAllPlayers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
