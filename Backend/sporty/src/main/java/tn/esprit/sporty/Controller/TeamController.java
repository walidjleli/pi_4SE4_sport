package tn.esprit.sporty.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.Team;
import tn.esprit.sporty.Service.IteamService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class TeamController {

    private final IteamService teamService;

    @GetMapping("/getAll")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable int id) {
        Optional<Team> team = teamService.getTeamById(id);
        return team.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addteam")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        System.out.println("Received Team: " + team);

        Team savedTeam = teamService.createTeam(team);
        return ResponseEntity.ok(savedTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable int id, @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        return updatedTeam != null ? ResponseEntity.ok(updatedTeam) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) {
        return teamService.deleteTeam(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
