import { Component, OnInit } from '@angular/core';
import { PlayerService } from 'src/app/services/player.service'; // ✅ Ajoute l'import correct

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.css']
})
export class PlayersComponent implements OnInit {
  players: any[] = [];

  constructor(private playerService: PlayerService) {}

  ngOnInit(): void {
    this.getPlayers();
  }

  getPlayers() {
    this.playerService.getAllPlayers().subscribe({
      next: (data: any[]) => {
        this.players = data;
      },
      error: (err: any) => {
        console.error('Erreur lors du chargement des joueurs :', err);
      }
    });
  }
}
  