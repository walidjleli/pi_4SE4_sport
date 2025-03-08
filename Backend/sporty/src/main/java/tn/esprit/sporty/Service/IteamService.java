package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.Team;

import java.util.List;
import java.util.Optional;

public interface IteamService {
    List<Team> getAllTeams();
    Optional<Team> getTeamById(int id);
    Team createTeam(Team team);
    Team updateTeam(int id, Team teamDetails);
    boolean deleteTeam(int id);
    public boolean removeDoctorFromTeam(int teamId);
    public boolean removeCoachFromTeam(int teamId);
    public boolean removePlayerFromTeam(int teamId, int playerId);
    public Team assignDoctorToTeam(int teamId, int doctorId);
    public Team assignCoachToTeam(int teamId, int coachId);
    public Team addPlayerToTeam(int teamId, int playerId);

}
