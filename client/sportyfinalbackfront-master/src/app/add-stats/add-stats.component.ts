import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Stats } from '../models/stats';
import { StatsService } from '../services/stats.service';
import { UserService } from '../services/user.service';
import { User } from '../models/user';

@Component({
  selector: 'app-add-stats',
  templateUrl: './add-stats.component.html',
  styleUrls: ['./add-stats.component.css']
})
export class AddStatsComponent implements OnInit {
  stats: Stats = new Stats(0, 0, 0, 0, 0);
  userId: number = 0;
  players: User[] = [];
  message: string = "";
  success: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private statsService: StatsService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadPlayers();

    const params = this.route.snapshot.paramMap;
    const userId = params.get('userId');

    if (userId) {
      this.userId = +userId;
    }
  }

  loadPlayers() {
    this.userService.getAllUsers().subscribe({
      next: (users: User[]) => {  // ✅ Correction des types
        this.players = users.filter((user: User) => user.role === 'PLAYER');
      },
      error: (err: any) => console.error("❌ Erreur lors du chargement des joueurs :", err)
    });
  }

  onPlayerSelected(event: Event) {
    const selectedUserId = +(event.target as HTMLSelectElement).value;
    this.userId = selectedUserId;
  }

  onSubmit() {
    if (!this.userId) {
      this.message = "Veuillez sélectionner un joueur.";
      this.success = false;
      return;
    }

    console.log('📡 Envoi des statistiques :', this.stats);
    console.log("🔍 UserID utilisé pour l'envoi :", this.userId);

    this.statsService.addStats(this.stats, this.userId).subscribe({
      next: (response) => {
        this.message = "✅ Statistiques ajoutées avec succès !";
        this.success = true;
        console.log(response);
      },
      error: (error) => {
        this.message = "❌ Erreur lors de l'ajout des statistiques.";
        this.success = false;
        console.error(error);
      }
    });
  }
}
