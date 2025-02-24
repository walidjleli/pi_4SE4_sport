package tn.esprit.sporty.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.Team;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.TeamRepository;
import tn.esprit.sporty.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class teamServiceImpl implements IteamService{
    private final TeamRepository teamRepository;
    private final UserRepository   userRepository;

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> getTeamById(int id) {
        return teamRepository.findById(id);
    }
    @Override
    public Team createTeam(Team team) {
        System.out.println("Players from request: " + team.getPlayers());

        // Ensure players list is not empty
        if (team.getPlayers() == null || team.getPlayers().isEmpty()) {
            throw new IllegalArgumentException("No players provided in the request.");
        }

        // Fetch player IDs from the request
        List<Integer> playerIds = team.getPlayers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        System.out.println("Player IDs from request: " + playerIds);

        // Fetch the players from the database
        List<User> players = userRepository.findAllById(playerIds);

        // Log the fetched players
        System.out.println("Fetched players: " + players);

        // Ensure all players were found
        if (players.size() != playerIds.size()) {
            throw new IllegalArgumentException("Some players not found in the database.");
        }

        // Add players to the team
        team.setPlayers(players);
        for (User user : players) {
            user.getTeams().add(team); // Add the team to the user's list
        }
        // Ensure coach and doctor are assigned (if provided)
        if (team.getCoach() != null && !team.getCoach().getTeams().contains(team)) {
            team.getCoach().getTeams().add(team);
        }

        if (team.getDoctor() != null && !team.getDoctor().getTeams().contains(team)) {
            team.getDoctor().getTeams().add(team);
        }

        // Save and return the team
        return teamRepository.save(team);
    }


    @Override
    public Team updateTeam(int id, Team teamDetails) {
        return teamRepository.findById(id)
                .map(team -> {
                    team.setTeamName(teamDetails.getTeamName());
                    team.setSubgroups(teamDetails.getSubgroups());
                    team.setPlayers(teamDetails.getPlayers());
                    team.setCoach(teamDetails.getCoach());
                    team.setDoctor(teamDetails.getDoctor());
                    return teamRepository.save(team);
                }).orElse(null);
    }

    @Override
    public boolean deleteTeam(int id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
