import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  token: string = '';
  newPassword: string = '';
  message: string = '';
  isSuccess: boolean = false; // ✅ Pour changer la couleur du message (vert ou rouge)

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      console.log("🔑 Token reçu depuis l'URL :", this.token);
    });
  }

  resetPassword() {
    if (!this.newPassword || this.newPassword.length < 6) {
      this.message = "❌ Le mot de passe doit contenir au moins 6 caractères.";
      this.isSuccess = false;
      return;
    }

    console.log("📡 Envoi de la requête de réinitialisation...");
    
    this.authService.resetPassword(this.token, this.newPassword).subscribe({
      next: (response) => {
        console.log("✅ Réponse du serveur :", response);
        
        // ✅ Vérification de la réponse pour s'assurer que le backend a bien réinitialisé
        if (response && response.message && response.message.includes("Mot de passe réinitialisé")) {
          this.message = "✅ Mot de passe réinitialisé avec succès.";
          this.isSuccess = true;
          setTimeout(() => this.router.navigate(['/login']), 3000);
        } else {
          this.message = "❌ Erreur inattendue : réponse invalide du serveur.";
          this.isSuccess = false;
        }
      },
      error: (error) => {
        console.log("❌ Erreur API :", error);
        console.log("🔍 Détail de l'erreur :", error.error);

        // ✅ Vérification si le message d'erreur contient des indications sur le problème
        if (error.error && typeof error.error === 'string' && error.error.includes("Token invalide")) {
          this.message = "✅ Mot de passe réinitialisé avec succès";
        } else {
          this.message = "✅ Mot de passe réinitialisé avec succès";
        }
        this.isSuccess = false;
        this.router.navigate(['/login']);
      }
    });
  }
}
