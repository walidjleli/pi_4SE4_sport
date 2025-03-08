import { Component, OnInit } from '@angular/core';
import { TeamService } from '../../services/team.service';
import { Team } from '../../models/team';
import { User } from '../../models/user';

@Component({
  selector: 'app-team-management',
  templateUrl: './team-management.component.html',
  styleUrls: ['./team-management.component.css']
})
export class TeamManagementComponent implements OnInit {
  teams: Team[] = [];
  players: User[] = [];
  coaches: User[] = [];
  doctors: User[] = [];
  selectedTeam: Team | null = null;
    newTeamName: string = '';

  constructor(private teamService: TeamService) {}

  ngOnInit(): void {
    console.log("🚀 Initialisation...");
    this.loadTeams();
    this.loadUsers();
  }

  loadTeams() {
    this.teamService.getTeams().subscribe({
      next: (data) => {
        console.log("✅ Équipes chargées :", data);
        this.teams = data;
      },
      error: (err) => {
        console.error("❌ Erreur lors du chargement des équipes :", err);
      }
    });
  }

  loadUsers(): void {
    this.teamService.getAllUsers().subscribe({
      next: (users) => {
        console.log("✅ Utilisateurs chargés :", users);
        this.players = users.filter(user => user.role?.toUpperCase() === 'PLAYER');
        this.coaches = users.filter(user => user.role?.toUpperCase() === 'COACH');
        this.doctors = users.filter(user => user.role?.toUpperCase() === 'DOCTOR');
        console.log("🛠 Players:", this.players);
        console.log("🛠 Coaches:", this.coaches);
        console.log("🛠 Doctors:", this.doctors);
      },
      error: (err) => {
        console.error("❌ Erreur lors du chargement des utilisateurs :", err);
      }
    });
  }

  addTeam() {
    if (this.newTeamName.trim() !== '') {
      const newTeam: Team = {
        teamId: 0,
        teamName: this.newTeamName,
        players: [],
        coach: undefined,
        doctor: undefined
      };

      this.teamService.addTeam(newTeam).subscribe({
        next: (createdTeam) => {
          console.log("✅ Équipe ajoutée :", createdTeam);
          this.loadTeams();
          this.newTeamName = '';
        },
        error: (err) => {
          console.error("❌ Erreur lors de l'ajout de l'équipe :", err);
        }
      });
    }
  }

  deleteTeam(id: number) {
    if (!id) return;

    this.teamService.deleteTeam(id).subscribe({
      next: () => {
        console.log(`🗑 Équipe ID=${id} supprimée.`);
        this.loadTeams();
      },
      error: (err) => {
        console.error(`❌ Erreur lors de la suppression de l'équipe ID=${id} :`, err);
      }
    });
  }

  selectTeam(team: Team) {
    this.selectedTeam = null; // 🔥 Réinitialise pour éviter un affichage incorrect

    // 📡 Récupérer les détails de l'équipe sélectionnée depuis le backend
    this.teamService.getTeamById(team.teamId!).subscribe({
        next: (updatedTeam) => {
            console.log("📡 Équipe chargée :", updatedTeam);
            this.selectedTeam = updatedTeam; // ✅ Met à jour l'équipe sélectionnée
        },
        error: (err) => {
            console.error("❌ Erreur chargement équipe :", err);
        }
    });
}

  
  

onPlayerSelected(event: Event): void {
  const playerId = +(event.target as HTMLSelectElement).value;
  if (!this.selectedTeam?.teamId || !playerId) {
      console.error("❌ Erreur : ID équipe ou joueur manquant !");
      return;
  }

  console.log(`📡 Vérification et ajout du joueur ID=${playerId} à l'équipe ID=${this.selectedTeam.teamId}`);

  this.teamService.addPlayerToTeam(this.selectedTeam.teamId, playerId).subscribe({
      next: (updatedTeam) => {
          console.log("✅ Joueur ajouté :", updatedTeam);
          this.selectedTeam!.players = updatedTeam.players;
          this.loadTeams();
      },
      error: (err) => {
          if (err.status === 409) {
              alert("⚠️ Ce joueur appartient déjà à une autre équipe !");
          } else {
              console.error("❌ Erreur ajout joueur :", err);
          }
      }
  });
}




  removePlayer(player: User) {
    if (!this.selectedTeam || !this.selectedTeam.teamId) {
        console.error("❌ Erreur : Aucune équipe sélectionnée !");
        return;
    }

    console.log(`🗑 Suppression du joueur ID=${player.id} de l'équipe ID=${this.selectedTeam.teamId}`);

    this.teamService.removePlayerFromTeam(this.selectedTeam.teamId, player.id).subscribe({
        next: () => {
            console.log("✅ Joueur supprimé de l'équipe !");
            if (this.selectedTeam && this.selectedTeam.players) {
                this.selectedTeam.players = this.selectedTeam.players.filter(p => p.id !== player.id);
            }
        },
        error: (err) => console.error("❌ Erreur suppression joueur :", err),
    });
  }

  onCoachSelected(event: Event): void {
    const coachId = +(event.target as HTMLSelectElement).value;
    if (!this.selectedTeam?.teamId || !coachId) {
        console.error("❌ Erreur : Aucune équipe sélectionnée ou coach ID manquant !");
        return;
    }

    console.log(`📡 Affectation du coach ID=${coachId} à l'équipe ID=${this.selectedTeam.teamId}`);

    this.teamService.addCoachToTeam(this.selectedTeam.teamId, coachId).subscribe({
        next: (updatedTeam) => {
            console.log("✅ Coach affecté :", updatedTeam);
            this.selectedTeam!.coach = updatedTeam.coach;
        },
        error: (err) => console.error("❌ Erreur affectation coach :", err),
    });
}

onDoctorSelected(event: Event): void {
  const doctorId = +(event.target as HTMLSelectElement).value;
  if (!this.selectedTeam?.teamId || !doctorId) {
      console.error("❌ Erreur : Aucune équipe sélectionnée ou docteur ID manquant !");
      return;
  }

  console.log(`📡 Vérification et affectation du docteur ID=${doctorId} à l'équipe ID=${this.selectedTeam.teamId}`);

  this.teamService.addDoctorToTeam(this.selectedTeam.teamId, doctorId).subscribe({
      next: (updatedTeam) => {
          console.log("✅ Docteur affecté :", updatedTeam);
          this.selectedTeam!.doctor = updatedTeam.doctor;
      },
      error: (err) => {
          if (err.status === 409) {
              alert("⚠️ Ce docteur est déjà affecté à une autre équipe !");
          } else if (err.status === 404) {
              alert("❌ Équipe ou docteur introuvable !");
          } else {
              console.error("❌ Erreur affectation docteur :", err);
          }
      }
  });
}


}
