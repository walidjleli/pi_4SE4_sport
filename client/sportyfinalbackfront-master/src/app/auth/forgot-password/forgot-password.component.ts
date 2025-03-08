import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  email: string = '';
  message: string = '';
  isSuccess: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  requestReset() {
    console.log("📡 Envoi de la demande de réinitialisation pour :", this.email);
    
    this.authService.requestPasswordReset(this.email).subscribe({
      next: (response) => {
        console.log("✅ Réponse du serveur :", response);
        this.message = "✅ Un lien de réinitialisation a été envoyé à votre e-mail.";
      },
      error: (err) => {
        console.error("❌ Erreur API :", err);
        this.message = `❌ Erreur : ${err.error || "Vérifiez votre e-mail et réessayez."}`;
      }
    });
  }
  
}
