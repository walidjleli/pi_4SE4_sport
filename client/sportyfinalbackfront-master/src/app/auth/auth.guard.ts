import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  if (!token) {
    router.navigate(['/login']); // ❌ Redirect unauthenticated users
    return false;
  }

  // ❌ If FAN tries to access back-office, redirect to front-office
  if (role === 'FAN' && state.url.startsWith('/back-office')) {
    router.navigate(['/front-office']);
    return false;
  }

  return true; // ✅ Allow access if authenticated
};
