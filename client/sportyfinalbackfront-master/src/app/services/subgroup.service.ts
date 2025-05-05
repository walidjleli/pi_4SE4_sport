import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Team } from '../models/team';
import { Subgroup } from '../models/subgroup';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class SubgroupService {
  private baseUrl = 'http://localhost:8090/api/subgroups';

  // ✅ Ajout de cette ligne pour corriger le bug
  private apiUrl = 'http://localhost:8090/api';

  constructor(private http: HttpClient) {}

  getAllTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(`${this.baseUrl}/teams`);
  }

  createSubgroup(teamId: number, subgroup: Subgroup): Observable<Subgroup> {
    return this.http.post<Subgroup>(`${this.baseUrl}/create/${teamId}`, subgroup);
  }

  getSubgroupsByTeamId(teamId: number): Observable<Subgroup[]> {
    return this.http.get<Subgroup[]>(`${this.apiUrl}/subgroups/team/${teamId}`);
  }
  

  getSubgroupById(id: number): Observable<Subgroup> {
    return this.http.get<Subgroup>(`${this.baseUrl}/${id}`);
  }

  deleteSubgroup(subgroupId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${subgroupId}`);
  }

  getUsersWithoutSubgroup(teamId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/team/${teamId}/users/withoutSubgroup`);
  }

  assignUserToSubgroup(subgroupId: number, userId: number, headers: HttpHeaders): Observable<any> {
    return this.http.put(`${this.baseUrl}/${subgroupId}/assignUser/${userId}`, {}, {
      headers,
      responseType: 'text' // ✅ évite le parsing JSON si le backend retourne du texte
    });
  }
  

  removeUserFromSubgroup(subgroupId: number, userId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/subgroups/${subgroupId}/removeUser/${userId}`, {}, { responseType: 'text' });
  }

  getUsersBySubgroupId(subgroupId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/users/${subgroupId}`);
  }

  // subgroup.service.ts
getAllSubgroups(): Observable<Subgroup[]> {
  return this.http.get<Subgroup[]>('http://localhost:8090/api/subgroups/all');
}
getSubgroupsWithTeams(): Observable<any[]> {
  return this.http.get<any[]>(`http://localhost:8090/api/subgroups/with-teams`);
} 

}
