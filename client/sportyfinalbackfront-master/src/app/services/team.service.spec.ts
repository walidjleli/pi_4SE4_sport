import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Team } from '../models/team';
import { User } from '../models/user';
import { HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private baseUrl = 'http://localhost:8090/RestTeam'; // Mets ton URL backend

  constructor(private http: HttpClient) {}

  // Récupérer toutes les équipes
  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(`${this.baseUrl}/getAll`);
  }

  // Ajouter une nouvelle équipe
  addTeam(team: Team): Observable<Team> {
    return this.http.post<Team>(`${this.baseUrl}/addteam`, team);
  }

  // Supprimer une équipe
  deleteTeam(teamId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${teamId}`);
  }

  // Ajouter un joueur à une équipe
  addPlayerToTeam(teamId: number, playerId: number): Observable<any> {
    return this.http.put(
      `http://localhost:8090/RestTeam/${teamId}/addPlayer`,
      { playerId },
      this.getHttpOptions() // ✅ Utilisation ici
    );
  }

  // Définir un coach pour une équipe
  setCoach(teamId: number, userId: number): Observable<Team> {
    return this.http.put<Team>(`${this.baseUrl}/${teamId}/setCoach`, { userId });
  }

  // Définir un docteur pour une équipe
  setDoctorToTeam(teamId: number, doctorId: number): Observable<any> {
    return this.http.put(
      `http://localhost:8090/RestTeam/${teamId}/setDoctor`,
      { doctorId },
      this.getHttpOptions() // ✅ Utilisation ici
    );
  }

  // Récupérer tous les utilisateurs
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`http://localhost:8090/users/getAll`);
  }

  private getHttpOptions() {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };
  }
}
