import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = ''; // ✅ Utiliser `email` au lieu de `username`
  password: string = '';
  errorMessage: string = ''; // ✅ Stocker le message d'erreur

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    if (!this.email || !this.password) {
      alert('Veuillez entrer une adresse e-mail et un mot de passe.');
      return;
    }

    console.log("🔹 Tentative de connexion :", { email: this.email, password: this.password });

    this.authService.login({ email: this.email, password: this.password }).subscribe(
      (response: any) => {
        console.log("✅ Connexion réussie, token reçu :", response.token);
        localStorage.setItem('token', response.token);
        this.router.navigate(['/back-office']).then(() => {
          window.location.reload();
        });
      },
      (error: any) => {
        console.error("❌ Échec de connexion :", error);
        if (error.status === 400) {
          this.errorMessage = "❌ Adresse e-mail ou mot de passe incorrect.";
        } else {
          this.errorMessage = "❌ Une erreur s'est produite. Veuillez réessayer.";
        }
      }
    );
  }
}
