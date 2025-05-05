import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar-back',
  templateUrl: './navbar-back.component.html',
  styleUrls: ['./navbar-back.component.css']
})
export class NavbarBackComponent {

  userFirstName: string = 'Utilisateur';
  userRole: string = '';
  
  ngOnInit(): void {
    const storedFirstName = localStorage.getItem('firstName');
    const storedRole = localStorage.getItem('role');

    if (storedFirstName) this.userFirstName = storedFirstName;
    if (storedRole) this.userRole = storedRole;
  }

  logout() {
    localStorage.clear();
    window.location.href = '/login';
  }
  
  goToFrontOffice() {
    window.location.href = '/front-office';
  }
  
}
