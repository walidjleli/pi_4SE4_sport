import { Component, OnInit } from '@angular/core';
import { TrainingSessionService, TrainingSession, Attendance } from '../services/training-session.service';
import { Subgroup } from '../models/subgroup';
import { User } from '../models/user';
import { SubgroupService } from '../services/subgroup.service';
import { ToastrService } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-training',
  templateUrl: './training.component.html',
  styleUrls: ['./training.component.css']
})
export class TrainingComponent implements OnInit {
  sessions: TrainingSession[] = [];
  attendanceList: Attendance[] = [];
  sessionId: number | null = null;

  subgroups: Subgroup[] = [];
  selectedSubgroupId: number = 0;
  users: User[] = [];
  selectedPlayerId: number | null = null;
  recommendations: string[] = [];
  recommendationResult: string = '';

  presenceMap: { [userId: number]: boolean } = {};

  isSubmitting: boolean = false;

  newSession: TrainingSession = {
    name: '',
    startDate: new Date(),
    endDate: new Date(),
    subgroups: [],
  };

  constructor(
    private trainingService: TrainingSessionService,
    private subgroupService: SubgroupService,
    private toastr: ToastrService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.trainingService.getAllSessions().subscribe(sessions => {
      this.sessions = sessions;
    });

    // ✅ Appel des sous-groupes avec teams
    this.subgroupService.getSubgroupsWithTeams().subscribe(res => {
      this.subgroups = res.map((sg: any) => new Subgroup(
        sg.subgroupId,
        sg.subgroupName,
        sg.teamName,
        sg.users || [],               // Si dispo, sinon tableau vide
        sg.trainingSessions || [],    // Si dispo, sinon tableau vide
        `${sg.subgroupName} (${sg.teamName || ''})` // label affiché
      ));

      if (this.subgroups.length > 0) {
        this.selectedSubgroupId = this.subgroups[0].subgroupId;
        this.loadUsers();
      }
    });
  }

  loadUsers() {
    if (!this.selectedSubgroupId) return;

    this.trainingService.getUsersBySubgroup(this.selectedSubgroupId).subscribe({
      next: users => {
        this.users = users;
        console.log("Joueurs chargés :", users);
      },
      error: err => console.error("Erreur chargement des joueurs :", err)
    });
  }

  createTrainingSession(): void {
    if (!this.selectedSubgroupId) {
      this.toastr.warning('Veuillez sélectionner un sous-groupe pour la session.');
      return;
    }

    this.trainingService.createSession(this.newSession).subscribe({
      next: created => {
        this.sessions.push(created);
        this.sessionId = created.id!;
        this.trainingService.assignSubgroupToSession(this.sessionId, this.selectedSubgroupId).subscribe({
          next: () => this.toastr.success('Sous-groupe assigné à la session'),
          error: () => this.toastr.error('Assignation échouée')
        });
        this.newSession = { name: '', startDate: new Date(), endDate: new Date(), subgroups: [] };
        this.toastr.success('Session créée avec succès !');
        this.loadAttendanceForSession(this.sessionId);
      },
      error: () => this.toastr.error('Erreur lors de la création de la session')
    });
  }

  markSubgroupAttendance(): void {
    if (!this.sessionId || !this.selectedSubgroupId) return;
    this.isSubmitting = true;
    let completed = 0;
    const total = this.users.length;

    this.users.forEach(user => {
      const present = this.presenceMap[user.id] ?? false;
      this.trainingService.markAttendance(this.sessionId!, user.id, present).subscribe({
        next: () => {
          if (!present) {
            this.trainingService.sendAbsenceEmail(user.email).subscribe();
          }
          completed++;
          if (completed === total) this.afterAttendanceMarked();
        },
        error: () => {
          completed++;
          if (completed === total) this.afterAttendanceMarked();
        }
      });
    });
  }

  afterAttendanceMarked(): void {
    this.isSubmitting = false;
    this.loadAttendanceForSession(this.sessionId!);
  }

  loadAttendanceForSession(sessionId: number) {
    this.trainingService.getAttendanceForSession(sessionId).subscribe(attendance => {
      this.attendanceList = attendance;
    });
  }

  loadSessionsForSubgroup(subgroupId: number) {
    this.trainingService.getSessionsBySubgroup(subgroupId).subscribe(sessions => {
      this.sessions = sessions;
    });
  }

  onSubgroupChange(): void {
    if (!this.selectedSubgroupId) return;
    this.trainingService.getUsersBySubgroup(this.selectedSubgroupId).subscribe({
      next: data => {
        this.users = data;
        this.presenceMap = {};
        this.users.forEach(user => this.presenceMap[user.id] = true);
      },
      error: err => console.error("Erreur chargement des joueurs :", err)
    });
    this.loadSessionsForSubgroup(this.selectedSubgroupId);
  }

  onSessionChange(): void {
    console.log("Session sélectionnée :", this.sessionId);
  }

  getRecommendationForPlayer() {
    if (!this.selectedPlayerId) return;

    this.http.get<{ exercices_recommandes: string[] }>(`http://localhost:8090/api/ia/recommend/${this.selectedPlayerId}`).subscribe({
      next: data => {
        this.recommendationResult = data.exercices_recommandes.join(', ');
        this.recommendations = data.exercices_recommandes;
      },
      error: () => {
        this.recommendationResult = "Erreur lors de la prédiction.";
        this.recommendations = [];
      }
    });
  }
  showCreateSession: boolean = false;

toggleCreateSession(): void {
  this.showCreateSession = !this.showCreateSession;
}

}
