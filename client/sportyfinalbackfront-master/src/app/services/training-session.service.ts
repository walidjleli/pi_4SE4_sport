import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subgroup } from '../models/subgroup';
import { User } from '../models/user';

export interface TrainingSession {
  id?: number;
  name: string;
  startDate: Date;
  endDate: Date;
  subgroups?: Subgroup[];
}

export interface Attendance {
  id?: number;
  present: boolean;
  user: User;
  trainingSession: TrainingSession;
}

@Injectable({
  providedIn: 'root'
})
export class TrainingSessionService {
  private apiUrl = 'http://localhost:8090/api/trainingsessions';

  constructor(private http: HttpClient) {}

  // ✅ Créer une nouvelle session
  createSession(session: TrainingSession): Observable<TrainingSession> {
    return this.http.post<TrainingSession>(this.apiUrl, session);
  }

  // ✅ Marquer la présence d’un joueur
  markAttendance(sessionId: number, userId: number, present: boolean): Observable<any> {
    return this.http.put(`${this.apiUrl}/${sessionId}/attendance/${userId}`, { present });
  }
  

  // ✅ Marquer la présence pour tous les membres d’un sous-groupe
 /* markSubgroupAttendance(sessionId: number, subgroupId: number): Observable<string> {
    return this.http.put<string>(
      `${this.apiUrl}/${sessionId}/attendance/subgroup/${subgroupId}`,
      {},
      { responseType: 'text' as 'json' }
    );
  }*/

  // ✅ Récupérer les présences pour une session
  getSessionAttendance(sessionId: number): Observable<Attendance[]> {
    return this.http.get<Attendance[]>(`${this.apiUrl}/${sessionId}/attendance`);
  }

  // ✅ Récupérer les présences pour un joueur
  getUserAttendance(userId: number): Observable<Attendance[]> {
    return this.http.get<Attendance[]>(`${this.apiUrl}/user/${userId}/attendance`);
  }

  // ✅ Toutes les sessions
  getAllSessions(): Observable<TrainingSession[]> {
    return this.http.get<TrainingSession[]>(this.apiUrl);
  }

  // ✅ Tous les sous-groupes
  getAllSubgroups(): Observable<Subgroup[]> {
    return this.http.get<Subgroup[]>(`${this.apiUrl}/subgroups`);
  }

  // ✅ Assigner un sous-groupe à une session
  assignSubgroupToSession(sessionId: number, subgroupId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${sessionId}/assign-subgroup/${subgroupId}`, {});
  }

  getUsersBySubgroup(subgroupId: number) {
    return this.http.get<User[]>(`http://localhost:8090/api/trainingsessions/${subgroupId}/users`);
  }
  
  
  getSessionsBySubgroup(subgroupId: number): Observable<TrainingSession[]> {
    return this.http.get<TrainingSession[]>(`${this.apiUrl}/subgroup/${subgroupId}/sessions`);
  }
  getAttendanceForSession(sessionId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${sessionId}/attendance`);
  }

  sendAbsenceEmail(email: string): Observable<void> {
    return this.http.post<void>(`http://localhost:8090/api/trainingsessions/send-absence-email`, { email });
  }

  getRecommendationFromIA(userId: number): Observable<string[]> {
    return this.http.get<string[]>(`http://localhost:8090/api/ia/recommend/${userId}`);
  }
  getAllTrainingSessions(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8090/api/trainingsessions/getAll`);
  }  
  
  
}
