import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar-back',
  templateUrl: './sidebar-back.component.html',
  styleUrls: ['./sidebar-back.component.css']
})
export class SidebarBackComponent implements OnInit {
  userFirstName: string = 'Utilisateur';
  userRole: string = '';

  ngOnInit(): void {
    const storedFirstName = localStorage.getItem('firstName');
    const storedRole = localStorage.getItem('role');

    if (storedFirstName) this.userFirstName = storedFirstName;
    if (storedRole) this.userRole = storedRole;
  }
}
