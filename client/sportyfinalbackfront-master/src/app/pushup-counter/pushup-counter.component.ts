import { Component, OnInit } from '@angular/core';
import { AiPushupService } from 'src/app/services/ai-pushup.service';
import { PushupResultService } from 'src/app/services/pushup-result.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-pushup-counter',
  templateUrl: './pushup-counter.component.html',
  styleUrls: ['./pushup-counter.component.css']
})
export class PushupCounterComponent implements OnInit {
  selectedFile: File | null = null;
  pushupCount: number | null = null;
  isLoading = false;
  errorMsg = '';
  message = '';
  leaderboard: any[] = [];
  userId: number = 0;

  constructor(
    private aiService: AiPushupService,
    private resultService: PushupResultService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    if (user && user.id) {
      this.userId = Number(user.id); // 🔥 forcer l'ID en tant que number
      console.log("✅ Utilisateur connecté avec ID :", this.userId);
    } else {
      console.warn("❌ Aucun utilisateur ou ID invalide !");
    }
  
    this.getLeaderboard();
  }
  
  
  

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onSubmit(): void {
    if (!this.selectedFile) {
      this.errorMsg = 'Aucun fichier sélectionné.';
      return;
    }
  
    this.isLoading = true;
    this.pushupCount = null;
    this.errorMsg = '';
    this.message = '';
  
    console.log("▶️ Envoi de la vidéo à l'API IA...");
  
    this.aiService.uploadVideo(this.selectedFile).subscribe({
      next: (res) => {
        console.log("✅ Réponse IA reçue :", res);
        this.pushupCount = res.pushups;
  
        // Vérification que le userId est bien chargé
        if (this.pushupCount !== null && this.userId !== null) {
          console.log("💾 Sauvegarde du résultat pour l'utilisateur ID =", this.userId);
          this.savePushupScore(this.pushupCount);
          this.getLeaderboard(); // Mise à jour du tableau
        } else {
          console.warn("❌ Score ou User ID manquant.");
        }
  
        this.isLoading = false;
      },
      error: (err) => {
        console.error("❌ Erreur IA :", err);
        this.errorMsg = 'Erreur lors de l’analyse de la vidéo.';
        this.isLoading = false;
      }
    });
  }
  

  savePushupScore(score: number) {
    if (!this.userId || this.userId <= 0) {
      console.error("❌ User ID invalide ou non défini :", this.userId);
      this.message = 'Erreur : utilisateur non reconnu.';
      return;
    }
  
    this.resultService.submitScore(this.userId, score).subscribe({
      next: () => {
        this.message = '✅ Score enregistré !';
        this.getLeaderboard();
      },
      error: (err) => {
        console.error("❌ Erreur API lors de l'enregistrement :", err);
        this.message = 'Erreur lors de la sauvegarde.';
      }
    });
  }
  

  getLeaderboard() {
    this.resultService.getLeaderboard().subscribe({
      next: (data) => {
        this.leaderboard = data;
      }
    });
  }
}
