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
  selectedPlayer: User | null = null;
hasExistingStats: boolean = false;

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
      next: (users: User[]) => {
        this.players = users.filter(user => user.role === 'PLAYER');
      },
      error: (err) => console.error("Erreur chargement joueurs :", err)
    });
  }

  onPlayerSelected(event: Event) {
    const selectedUserId = +(event.target as HTMLSelectElement).value;
    this.userId = selectedUserId;
    this.selectedPlayer = this.players.find(p => p.id === this.userId) || null;
  
    this.statsService.getStatsByUserId(this.userId).subscribe({
      next: (existingStats) => {
        if (existingStats && existingStats.id) {
          this.stats = existingStats;
          this.hasExistingStats = true;
        } else {
          this.stats = new Stats(0, 0, 0, 0, 0);
          this.hasExistingStats = false;
        }
      },
      error: () => {
        this.stats = new Stats(0, 0, 0, 0, 0);
        this.hasExistingStats = false;
      }
    });
  }
  

  onSubmit() {
    if (!this.userId) {
      this.message = "Veuillez sélectionner un joueur.";
      this.success = false;
      return;
    }

    this.statsService.getStatsByUserId(this.userId).subscribe({
      next: (existingStats) => {
        if (existingStats && existingStats.id) {
          // Update
          this.statsService.updateStats(existingStats.id, this.stats).subscribe({
            next: () => {
              this.message = "✅ Statistiques mises à jour.";
              this.success = true;
            },
            error: () => {
              this.message = "❌ Erreur lors de la mise à jour.";
              this.success = false;
            }
          });
        } else {
          // Ajout
          this.statsService.addStats(this.stats, this.userId).subscribe({
            next: () => {
              this.message = "✅ Statistiques ajoutées.";
              this.success = true;
            },
            error: () => {
              this.message = "❌ Erreur lors de l'ajout.";
              this.success = false;
            }
          });
        }
      },
      error: () => {
        // Si erreur de récupération, on tente un ajout
        this.statsService.addStats(this.stats, this.userId).subscribe({
          next: () => {
            this.message = "✅ Statistiques ajoutées.";
            this.success = true;
          },
          error: () => {
            this.message = "❌ Erreur lors de l'ajout.";
            this.success = false;
          }
        });
      }
    });
  }
}
