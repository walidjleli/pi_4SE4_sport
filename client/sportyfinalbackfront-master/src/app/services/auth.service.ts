import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8090/rest/auth'; // Adjust API URL if needed

  constructor(private http: HttpClient, private router: Router) {}

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /** ✅ Register a new user */
  register(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, userData, this.httpOptions);
  }

  /** ✅ Login User */
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials, this.httpOptions).pipe(
      tap((response: any) => {
        if (response.token) {
          this.setToken(response.token);
          this.setUserRole(response.role); // Store user role
          this.redirectUser(response.role, response.token); // Redirect after login
        }
      })
    );
  }

  /** ✅ Store token in localStorage */
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  /** ✅ Retrieve token from localStorage */
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  /** ✅ Store user role in localStorage */
  setUserRole(role: string): void {
    localStorage.setItem('role', role);
  }

  /** ✅ Get user role */
  getUserRole(): string | null {
    return localStorage.getItem('role');
  }

  /** ✅ Check if user is authenticated */
  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  /** ✅ Logout and clear storage */
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['/login']); // Redirect to login after logout
  }

  /** ✅ Redirect user based on role with token in URL */
  redirectUser(role: string, token: string): void {
    if (role === 'FAN') {
      this.router.navigate(['/front-office'], { queryParams: { token: token } });
    } else {
      this.logout(); // Logout if the role is not FAN
    }
  }
}
