import { User } from "./user";

export class Team {
  teamId?: number;
  teamName: string;
  players: User[] = [];
  coach?: User;
  doctor?: User;

  constructor(teamName: string) {
    this.teamName = teamName;
  }
}
