export class Stats {
    id!: number;
    goalsScored!: number;
    assists!: number;
    minutesPlayed!: number;
    interceptions!: number;
    successfulPasses!: number;
    userId?: number;  // ID du joueur associé (optionnel)
  
    constructor(
      goalsScored: number,
      assists: number,
      minutesPlayed: number,
      interceptions: number,
      successfulPasses: number,
      userId?: number
    ) {
      this.goalsScored = goalsScored;
      this.assists = assists;
      this.minutesPlayed = minutesPlayed;
      this.interceptions = interceptions;
      this.successfulPasses = successfulPasses;
      this.userId = userId;
    }
  }
  