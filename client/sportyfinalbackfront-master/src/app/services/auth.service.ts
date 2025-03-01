import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8090/rest/auth'; // 🔹 API URL

  constructor(private http: HttpClient, private router: Router) {}

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /** ✅ Register a new user */
  register(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, userData, this.httpOptions);
  }

  /** ✅ Login and fetch user details */
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials, this.httpOptions).pipe(
      tap((response: any) => {
        if (response.token && response.role) { // 🔥 Vérifier si le rôle est bien renvoyé
          this.setToken(response.token);
          this.setUserRole(response.role);
          this.setFirstName(response.firstName);
          this.redirectUser(response.role);
        }
      })
    );
  }

  /** ✅ Store token in localStorage */
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  /** ✅ Store role in localStorage */
  setUserRole(role: string): void {
    localStorage.setItem('role', role);
  }

  /** ✅ Store first name in localStorage */
  setFirstName(firstName: string): void {
    localStorage.setItem('firstName', firstName);
  }

  /** ✅ Retrieve token */
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  /** ✅ Retrieve user role */
  getUserRole(): string | null {
    return localStorage.getItem('role');
  }

  /** ✅ Check if user is authenticated */
  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  /** ✅ Logout and clear storage */
  logout(): void {
    localStorage.clear();
    this.router.navigate(['/front-office']); // 🔥 Redirect to home after logout
  }

  /** ✅ Redirect user based on role */
  redirectUser(role: string): void {
    if (role === 'ADMIN' || role === 'COACH' || role === 'DOCTOR') {
      this.router.navigate(['/back-office']);
    } else {
      this.router.navigate(['/front-office']);
    }
  }
}
