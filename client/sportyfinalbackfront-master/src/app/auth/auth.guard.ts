import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  // 🔹 Si l'utilisateur n'est pas connecté, rediriger vers le front-office
  if (!token) {
    router.navigate(['/front-office']);
    return false;
  }

  // 🔹 Vérifier si l'utilisateur essaie d'accéder au Back-Office
  if (state.url.startsWith('/back-office')) {
    // ❌ Si l'utilisateur est un FAN, il ne peut pas aller au Back-Office
    if (role === 'FAN') {
      router.navigate(['/front-office']);
      return false;
    }
  }

  return true; // ✅ Autoriser l'accès
};
