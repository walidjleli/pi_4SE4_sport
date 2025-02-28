import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Stats } from '../models/stats';
import { StatsService } from '../services/stats.service';

@Component({
  selector: 'app-add-stats',
  templateUrl: './add-stats.component.html',
  styleUrls: ['./add-stats.component.css']
})
export class AddStatsComponent implements OnInit {
  stats: Stats = new Stats(0, 0, 0, 0, 0);
  userId: number = 0;

  constructor(private route: ActivatedRoute, private statsService: StatsService) {}

  ngOnInit(): void {
    const params = this.route.snapshot.paramMap;
    const userId = params.get('userId');
  
    console.log("UserID récupéré de l'URL :", userId); // Debug
  
    if (userId) {
      this.userId = +userId;  // Convertir en nombre
      this.stats.userId = this.userId;
    }
  
    console.log("UserID après conversion :", this.userId); // Debug
  }
  

  onSubmit() {
    console.log('Données envoyées :', this.stats);
    this.statsService.addStats(this.stats, this.userId).subscribe(
      (response) => {
        console.log('Statistiques ajoutées avec succès !', response);
        alert('Statistiques ajoutées avec succès !'); 
      },
      (error) => {
        console.error('Erreur lors de l\'ajout des statistiques :', error);
      }
    );
  }
}
