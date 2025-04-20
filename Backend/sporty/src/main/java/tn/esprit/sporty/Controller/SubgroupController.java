package tn.esprit.sporty.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.Subgroup;
import tn.esprit.sporty.Entity.Team;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.SubgroupRepository;
import tn.esprit.sporty.Service.ISubgroupService;
import tn.esprit.sporty.Service.IteamService;
import tn.esprit.sporty.Service.UserService;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/subgroups")
@RequiredArgsConstructor
@Slf4j
public class SubgroupController {

    private final ISubgroupService subgroupService;
    private final IteamService teamService;
    private final SubgroupRepository subgroupRepository;
    private final UserService userService;

    // ✅ Créer un sous-groupe depuis les joueurs d'une équipe sélectionnée
    @PostMapping("/create/{teamId}")
    public ResponseEntity<Subgroup> createSubgroup(@PathVariable int teamId, @RequestBody Subgroup subgroup) {
        log.info("📌 Création d'un sous-groupe pour l'équipe ID={}", teamId);
        Subgroup createdSubgroup = subgroupService.createSubgroupFromTeam(teamId, subgroup);
        return ResponseEntity.ok(createdSubgroup);
    }

    // ✅ Récupérer tous les sous-groupes
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Subgroup>> getSubgroupsByTeamId(@PathVariable int teamId) {
        List<Subgroup> subgroups = subgroupService.getSubgroupsByTeamId(teamId);
        if (subgroups.isEmpty()) {
            // Si aucun sous-groupe n'est trouvé, renvoyer une réponse 404 ou une réponse vide
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        return ResponseEntity.ok(subgroups);
    }


    // ✅ Récupérer un sous-groupe par ID
    @GetMapping("/{id}")
    public ResponseEntity<Subgroup> getSubgroupById(@PathVariable int id) {
        return ResponseEntity.ok(subgroupService.getSubgroupById(id));
    }

    // ✅ Mettre à jour un sous-groupe
    @PutMapping("/update/{id}")
    public ResponseEntity<Subgroup> updateSubgroup(@PathVariable int id, @RequestBody Subgroup subgroup) {
        Subgroup updatedSubgroup = subgroupService.updateSubgroup(id, subgroup);
        return ResponseEntity.ok(updatedSubgroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSubgroup(@PathVariable int id) {
        try {
            subgroupService.deleteSubgroup(id);

            // Retourner un objet JSON avec un message de succès
            Map<String, String> response = new HashMap<>();
            response.put("message", "Sous-groupe supprimé avec succès et les joueurs ont été libérés !");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En cas d'erreur, retourner un message d'erreur en JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erreur lors de la suppression du sous-groupe");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    // ✅ Affecter un joueur à un sous-groupe
    @PutMapping("/{subgroupId}/assignUser/{userId}")
    public ResponseEntity<String> assignPlayerToSubgroup(
            @PathVariable int subgroupId,
            @PathVariable("userId") int userId) {

        boolean assigned = subgroupService.assignPlayerToSubgroup(subgroupId, userId);

        if (assigned) {
            return ResponseEntity.ok("✅ Joueur affecté au sous-groupe");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Affectation échouée");
        }
    }



    @GetMapping("/teams")  // The URL should be correctly defined
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/team/{teamId}/users/withoutSubgroup")
    public ResponseEntity<List<User>> getUsersWithoutSubgroup(@PathVariable int teamId) {
        try {
            List<User> usersWithoutSubgroup = subgroupService.findUsersWithoutSubgroup(teamId);
            return ResponseEntity.ok(usersWithoutSubgroup);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des utilisateurs sans sous-groupe", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users/{subgroupId}")
    public ResponseEntity<List<User>> getUsersBySubgroupId(@PathVariable int subgroupId) {
        try {
            List<User> users = subgroupService.getUsersBySubgroupId(subgroupId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des utilisateurs du sous-groupe", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{subgroupId}/removeUser/{userId}")
    public ResponseEntity<?> removeUserFromSubgroup(@PathVariable int subgroupId, @PathVariable int userId) {

        try {
            subgroupService.removeUserFromSubgroup( subgroupId, userId);
            return ResponseEntity.ok("Utilisateur retiré avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du retrait de l'utilisateur");
        }
    }

    @GetMapping("/all")
    public List<Subgroup> getAllSubgroups() {
        return subgroupService.getAllSubgroups();
    }




}
