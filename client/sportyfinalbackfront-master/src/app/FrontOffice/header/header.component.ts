import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  firstName: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadUserDetails();
  }

  loadUserDetails(): void {
    this.isLoggedIn = this.authService.isAuthenticated();
    this.firstName = localStorage.getItem('firstName'); // ✅ Get first name from localStorage
  }

  logout(): void {
    this.authService.logout();
    this.isLoggedIn = false;
    this.firstName = null;
  }
}
