import { Component, OnInit } from '@angular/core';
import { SubgroupService } from '../services/subgroup.service';
import { Subgroup } from '../models/subgroup';
import { Team } from '../models/team';
import { ToastrService } from 'ngx-toastr';
import { User } from '../models/user';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-subgroup-management',
  templateUrl: './subgroup-management.component.html',
  styleUrls: ['./subgroup-management.component.css'],
})
export class SubgroupManagementComponent implements OnInit {
  teams: Team[] = [];
  subgroups: Subgroup[] = [];
  selectedTeam: Team | null = null;
  newSubgroupName: string = '';
  subgroupIdToDelete: number | null = null;
  errorMessage: string | null = null;
  usersWithoutSubgroup: User[] = [];
  subgroupUsers: User[] = [];
  selectedSubgroup: Subgroup | null = null;
  subgroupUsersMap: { [subgroupId: number]: User[] } = {};


  constructor(
    private subgroupService: SubgroupService,
    private toastr: ToastrService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadTeams();
  }

  loadTeams(): void {
    this.subgroupService.getAllTeams().subscribe({
      next: (teams) => (this.teams = teams),
      error: () => this.toastr.error("Erreur de chargement des équipes"),
    });
  }

  loadSubgroups(): void {
    if (this.selectedTeam && this.selectedTeam.teamId) {
      this.subgroupService.getSubgroupsByTeamId(this.selectedTeam.teamId).subscribe({
        next: (subgroups) => {
          this.subgroups = subgroups;
          this.errorMessage = '';
        },
        error: (error) => {
          if (error.status === 404) {
            this.subgroups = [];
            this.errorMessage = "Aucun sous-groupe trouvé pour cette équipe.";
          } else {
            console.error("Erreur lors du chargement des sous-groupes", error);
            this.errorMessage = "Erreur lors du chargement des sous-groupes.";
          }
        }
      });
      
    }
  }
  

  createSubgroup(): void {
    if (!this.selectedTeam?.teamId) return;

    const newSubgroup = new Subgroup(
      0,                               // subgroupId (par défaut, 0 ou null)
      this.newSubgroupName,            // subgroupName
      this.selectedTeam.teamName,      // teamName (prends teamName depuis l’objet Team)
      [],                              // users
      [],                              // trainingSessions
      `${this.newSubgroupName} (${this.selectedTeam.teamName || ''})` // label
    );
        this.subgroupService.createSubgroup(this.selectedTeam.teamId, newSubgroup).subscribe({
      next: () => {
        this.toastr.success('Sous-groupe créé');
        this.newSubgroupName = '';
        this.loadSubgroups();
      },
      error: (err) => {
        if (err.status === 403 || err.status === 400) {
          this.errorMessage = 'Nom du sous-groupe déjà utilisé.';
        } else {
          this.toastr.error("Erreur lors de la création");
        }
      },
    });
  }

  confirmDelete(subgroupId: number): void {
    this.subgroupIdToDelete = subgroupId;
  }
  
  deleteSubgroup(subgroupId: number): void {
    this.subgroupService.deleteSubgroup(subgroupId).subscribe({
      next: (res) => {
        this.toastr.success('✅ Sous-groupe supprimé avec succès');
  
        // Supprime le sous-groupe localement sans attendre un reload du backend
        this.subgroups = this.subgroups.filter(sg => sg.subgroupId !== subgroupId);
  
        // Réinitialise les variables locales
        this.subgroupIdToDelete = null;
        this.selectedSubgroup = null;
        this.subgroupUsers = [];
      },
      error: () => {
        this.toastr.warning('⚠️ Erreur lors de la suppression');
        this.subgroupIdToDelete = null;
      }
    });
  }
  
  cancelDelete(): void {
    this.subgroupIdToDelete = null;
  }
  

  openUserManagement(subgroup: Subgroup): void {
    this.selectedSubgroup = subgroup;
    if (!this.selectedTeam?.teamId) return;

    this.subgroupService.getUsersWithoutSubgroup(this.selectedTeam.teamId).subscribe({
      next: (users) => (this.usersWithoutSubgroup = users),
      error: () => this.toastr.error("Erreur chargement utilisateurs dispo"),
    });

    if (subgroup.subgroupId) {
      this.subgroupService.getUsersBySubgroupId(subgroup.subgroupId).subscribe({
        next: (users) => (this.subgroupUsers = users),
        error: () => this.toastr.error("Erreur chargement utilisateurs affectés"),
      });
    }
  }

  addUserToSubgroup(user: User): void {
    if (!this.selectedSubgroup?.subgroupId) {
      this.toastr.warning('Aucun sous-groupe sélectionné');
      return;
    }
  
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
  
    this.subgroupService.assignUserToSubgroup(this.selectedSubgroup.subgroupId, user.id, headers).subscribe({
      next: () => {
        this.toastr.success("L'utilisateur a été affecté au sous-groupe avec succès.");
        this.openUserManagement(this.selectedSubgroup!); // ✅ recharge affectés + disponibles
      },
      error: (error) => {
        console.error("Erreur lors de l'ajout de l'utilisateur", error);
        this.toastr.error("Erreur lors de l'affectation de l'utilisateur");
      }
    });
  }
  
  

  removeUserFromSubgroup(user: User, subgroupId: number | undefined): void {
    if (!subgroupId) {
      this.toastr.warning("ID du sous-groupe manquant.");
      return;
    }
  
    this.subgroupService.removeUserFromSubgroup(subgroupId, user.id).subscribe({
      next: () => {
        this.toastr.success("L'utilisateur a été retiré du sous-groupe.");
        this.subgroupUsers = this.subgroupUsers.filter(u => u.id !== user.id);
        this.usersWithoutSubgroup.push(user); // rendre dispo à nouveau
      },
      error: (error) => {
        console.error("Erreur lors du retrait", error);
        this.toastr.error("Erreur lors du retrait de l'utilisateur.");
      }
    });
  }
  
  

  loadSubgroupsWithTeams(): void {
    this.subgroupService.getSubgroupsWithTeams().subscribe({
      next: (res) => {
        this.subgroups = res; // [{ id, label }]
      },
      error: () => {
        this.toastr.warning('Erreur lors du chargement des sous-groupes');
      }
    });
  }
  
  
  

  loadUsersBySubgroup(subgroupId: number): void {
    this.subgroupService.getUsersBySubgroupId(subgroupId).subscribe({
      next: (users) => {
        this.subgroupUsersMap[subgroupId] = users;
      },
      error: (error) => {
        this.toastr.error("Erreur lors du chargement des utilisateurs");
        console.error(error);
      }
    });
  }
  
}
