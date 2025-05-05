import { Component, OnInit } from '@angular/core';
import { TrainingSessionService } from 'src/app/services/training-session.service'; // ✅ Créer ce service si besoin

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.css']
})
export class MatchesComponent implements OnInit {
  trainingSessions: any[] = [];

  constructor(private trainingSessionService: TrainingSessionService) {}

  ngOnInit(): void {
    this.getTrainingSessions();
  }

  getTrainingSessions() {
    this.trainingSessionService.getAllTrainingSessions().subscribe({
      next: (data) => {
        this.trainingSessions = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des sessions :', err);
      }
    });
  }
}
