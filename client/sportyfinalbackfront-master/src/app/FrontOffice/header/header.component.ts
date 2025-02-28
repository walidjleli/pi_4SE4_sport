import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  userName: string | null = null;

  constructor(private router: Router) {}

  ngOnInit() {
    const token = localStorage.getItem('token');
    this.isLoggedIn = !!token;

    // Retrieve and store the username from localStorage
    this.userName = localStorage.getItem('userName'); 
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userName');
    localStorage.removeItem('role');
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}
