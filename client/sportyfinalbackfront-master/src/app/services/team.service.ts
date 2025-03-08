import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Team } from '../models/team';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private apiUrl: string = 'http://localhost:8090/RestTeam';  

  constructor(private http: HttpClient) {}

  getTeams(): Observable<Team[]> {
    console.log("📡 Récupération des équipes...");
    return this.http.get<Team[]>(`${this.apiUrl}/getAll`);
  }

  addTeam(team: Team): Observable<Team> {
    console.log("📡 Ajout d'une équipe :", team);
    return this.http.post<Team>(`${this.apiUrl}/addteam`, team);
  }
  deleteTeam(id: number): Observable<any> {
    console.log(`📡 Suppression de l'équipe ID=${id}`);
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
}


  addPlayerToTeam(teamId: number, playerId: number): Observable<Team> {
    console.log(`📡 Ajout du joueur ID=${playerId} à l'équipe ID=${teamId}`);
    return this.http.put<Team>(`${this.apiUrl}/${teamId}/addPlayer`, { playerId });
  }

  removePlayerFromTeam(teamId: number, playerId: number): Observable<any> {
    console.log(`📡 Suppression du joueur ID=${playerId} de l'équipe ID=${teamId}`);
    return this.http.put(`${this.apiUrl}/${teamId}/removePlayer`, { playerId });
}


  addCoachToTeam(teamId: number, coachId: number): Observable<Team> {
    console.log(`📡 Affectation du coach ID=${coachId} à l'équipe ID=${teamId}`);
    return this.http.put<Team>(`${this.apiUrl}/${teamId}/setCoach`, { userId: coachId });
  }

  addDoctorToTeam(teamId: number, doctorId: number): Observable<Team> {
    const body = { doctorId: doctorId };
    console.log(`📡 Envoi au backend :`, body);

    return this.http.put<Team>(`${this.apiUrl}/${teamId}/setDoctor`, body);
}

setCoach(teamId: number, coachId: number): Observable<any> {
  return this.http.put(`http://localhost:8090/RestTeam/${teamId}/setCoach`, { userId: coachId });
}


  getAllUsers(): Observable<User[]> {
    console.log("📡 Récupération des utilisateurs...");
    return this.http.get<User[]>('http://localhost:8090/rest/auth/users');
  }
  getTeamById(teamId: number): Observable<Team> {
    console.log(`📡 Chargement des détails de l'équipe ID=${teamId}`);
    return this.http.get<Team>(`${this.apiUrl}/${teamId}`);
}

}
