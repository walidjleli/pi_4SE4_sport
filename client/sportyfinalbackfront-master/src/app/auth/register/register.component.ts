import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  userData = {
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    role: ''
  };

  roles: string[] = ['DOCTOR', 'COACH', 'PLAYER', 'ADMIN', 'FAN']; // 🔥 Liste des rôles

  errorMessage: string = ''; 

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (!this.userData.email || !this.userData.password || !this.userData.firstName || !this.userData.lastName || !this.userData.role) {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      return;
    }

    this.authService.register(this.userData).subscribe(
      (response) => {
        alert('Inscription réussie !');
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Échec de l\'inscription', error);
        this.errorMessage = 'Erreur lors de l’inscription. Veuillez réessayer.';
      }
    );
  }
}
