package tn.esprit.sporty.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // ✅ Ajout pour le logging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.Role;
import tn.esprit.sporty.Entity.Subgroup;
import tn.esprit.sporty.Entity.Team;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.SubgroupRepository;
import tn.esprit.sporty.Repository.UserRepository;
import tn.esprit.sporty.Service.IteamService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/RestTeam")
@RequiredArgsConstructor
@Slf4j // ✅ Ajout du logger
public class TeamController {
    @Autowired
    private SubgroupRepository subgroupRepository;

    private final IteamService teamService;
    private final UserRepository userRepository;

    // ✅ Récupérer toutes les équipes
    @GetMapping("/getAll")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        log.info("📌 Chargement de toutes les équipes. Nombre total : {}", teams.size());
        return ResponseEntity.ok(teams);
    }

    // ✅ Récupérer une équipe par ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable int id) {
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isPresent()) {
            log.info("🔍 Équipe trouvée : {}", team.get().getTeamName());
            return ResponseEntity.ok(team.get());
        } else {
            log.warn("❌ Équipe ID={} introuvable", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // ✅ Ajouter une équipe
    @PostMapping("/addteam")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        log.info("📥 Nouvelle équipe reçue : {}", team.getTeamName());

        // Sauvegarder l’équipe sans sous-groupes d’abord
        Team savedTeam = teamService.createTeam(team);

        // Créer les sous-groupes par défaut
        Subgroup defenseGroup = new Subgroup();
        defenseGroup.setSubgroupName("Défense");
        defenseGroup.setTeam(savedTeam);
        defenseGroup.setPlayers(new ArrayList<>());

        Subgroup attackGroup = new Subgroup();
        attackGroup.setSubgroupName("Attaque");
        attackGroup.setTeam(savedTeam);
        attackGroup.setPlayers(new ArrayList<>());

        // Sauvegarder les sous-groupes
        List<Subgroup> subgroups = new ArrayList<>();
        subgroups.add(defenseGroup);
        subgroups.add(attackGroup);

        savedTeam.setSubgroups(subgroups);

        subgroupRepository.saveAll(subgroups);
        teamService.updateTeam(savedTeam.getTeamId(), savedTeam);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeam);
    }


    // ✅ Mettre à jour une équipe
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable int id, @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        if (updatedTeam != null) {
            log.info("✅ Équipe ID={} mise à jour avec succès.", id);
            return ResponseEntity.ok(updatedTeam);
        } else {
            log.warn("❌ Équipe ID={} introuvable.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // ✅ Supprimer une équipe


    // ✅ Ajouter un joueur à une équipe
    @PutMapping("/{teamId}/addPlayer")
    public ResponseEntity<?> addPlayerToTeam(@PathVariable int teamId, @RequestBody Map<String, Integer> payload) {
        if (!payload.containsKey("playerId")) {
            return ResponseEntity.badRequest().body("❌ Erreur : `playerId` est requis !");
        }

        int playerId = payload.get("playerId");
        Optional<Team> teamOpt = teamService.getTeamById(teamId);
        Optional<User> playerOpt = userRepository.findById(playerId);

        if (teamOpt.isEmpty() || playerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Équipe ou joueur introuvable !");
        }

        Team team = teamOpt.get();
        User player = playerOpt.get();

        // 🚨 Vérifier si le joueur est déjà dans une autre équipe
        if (player.getTeam() != null && player.getTeam().getTeamId() != teamId) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("⚠️ Ce joueur est déjà dans une autre équipe !");
        }

        // ✅ Affecter l'équipe au joueur
        player.setTeam(team);
        userRepository.save(player); // Sauvegarder l'utilisateur mis à jour

        team.getPlayers().add(player);
        teamService.updateTeam(teamId, team); // Mettre à jour l'équipe

        // 🎯 Affectation automatique au bon sous-groupe
        List<Subgroup> subgroups = team.getSubgroups(); // Récupération des sous-groupes de l'équipe
        Subgroup targetGroup = null;

        switch (player.getPoste()) {
            case GARDIEN:
            case DEFENSEUR:
                targetGroup = subgroups.stream()
                        .filter(sg -> sg.getSubgroupName().equalsIgnoreCase("Défense"))
                        .findFirst().orElse(null);
                break;
            case MILIEU:
            case ATTAQUANT:
                targetGroup = subgroups.stream()
                        .filter(sg -> sg.getSubgroupName().equalsIgnoreCase("Attaque"))
                        .findFirst().orElse(null);
                break;
        }

        if (targetGroup != null) {
            player.setSubgroup(targetGroup);
            targetGroup.getPlayers().add(player);
            subgroupRepository.save(targetGroup);
            userRepository.save(player); // Sauvegarder à nouveau pour lier le sous-groupe
        }

        log.info("✅ Joueur ID={} ajouté à l'équipe ID={} et au sous-groupe {}",
                player.getId(), team.getTeamId(),
                targetGroup != null ? targetGroup.getSubgroupName() : "aucun");

        return ResponseEntity.ok(team);
    }




    // ✅ Affecter un coach à une équipe
    @PutMapping("/{teamId}/setCoach")
    public ResponseEntity<String> setCoach(@PathVariable int teamId, @RequestBody Map<String, Integer> payload) {
        if (!payload.containsKey("userId")) {
            return ResponseEntity.badRequest().body("❌ Erreur : `userId` est requis !");
        }

        int coachId = payload.get("userId");
        Optional<Team> teamOpt = teamService.getTeamById(teamId);
        Optional<User> coachOpt = userRepository.findById(coachId);

        if (teamOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Équipe introuvable !");
        }
        if (coachOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Coach introuvable !");
        }

        Team team = teamOpt.get();
        User coach = coachOpt.get();

        if (coach.getRole() != Role.COACH) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("⚠️ L'utilisateur sélectionné n'est pas un coach !");
        }

        // 🚨 Libérer le coach de l'équipe précédente sans le supprimer
        if (team.getCoach() != null) {
            User previousCoach = team.getCoach();
            previousCoach.setTeam(null);  // Dissocier le coach de l'équipe
            userRepository.save(previousCoach);  // Sauvegarder les modifications dans la base de données
            log.info("🆘 Coach ID={} libéré de l'équipe ID={}", previousCoach.getId(), teamId);
        }

        // 🚨 Vérifier si ce coach est déjà assigné à une autre équipe
        List<Team> allTeams = teamService.getAllTeams();
        for (Team t : allTeams) {
            if (t.getCoach() != null && t.getCoach().getId() == coachId && t.getTeamId() != teamId) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("⚠️ Ce coach est déjà affecté à une autre équipe !");
            }
        }

        // Affectation du coach
        team.setCoach(coach);
        teamService.updateTeam(teamId, team);

        return ResponseEntity.ok("✅ Coach affecté avec succès !");
    }




    // ✅ Affecter un docteur à une équipe
    @PutMapping("/{teamId}/setDoctor")
    public ResponseEntity<?> setDoctorForTeam(@PathVariable int teamId, @RequestBody Map<String, Integer> payload) {
        if (!payload.containsKey("doctorId")) {
            log.warn("❌ Requête invalide : `doctorId` est manquant !");
            return ResponseEntity.badRequest().body("❌ Erreur : `doctorId` est requis !");
        }

        int doctorId = payload.get("doctorId");
        log.info("📡 Tentative d'affectation du docteur ID={} à l'équipe ID={}", doctorId, teamId);

        Optional<Team> teamOpt = teamService.getTeamById(teamId);
        Optional<User> doctorOpt = userRepository.findById(doctorId);

        if (teamOpt.isEmpty()) {
            log.warn("❌ Équipe ID={} introuvable", teamId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Équipe introuvable !");
        }
        if (doctorOpt.isEmpty()) {
            log.warn("❌ Docteur ID={} introuvable", doctorId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Docteur introuvable !");
        }

        Team team = teamOpt.get();
        User doctor = doctorOpt.get();

        if (!Role.DOCTOR.equals(doctor.getRole())) {
            log.warn("❌ L'utilisateur ID={} n'est pas un docteur", doctorId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("⚠️ L'utilisateur sélectionné n'est pas un docteur !");
        }

        // 🚨 Libérer le docteur de l'équipe précédente sans le supprimer
        if (team.getDoctor() != null) {
            User previousDoctor = team.getDoctor();
            previousDoctor.setTeam(null);  // Dissocier le docteur de l'équipe
            userRepository.save(previousDoctor);  // Sauvegarder les modifications dans la base de données
            log.info("🆘 Docteur ID={} libéré de l'équipe ID={}", previousDoctor.getId(), teamId);
        }

        // 🚨 Vérification si le docteur est déjà affecté à une autre équipe
        List<Team> allTeams = teamService.getAllTeams();
        for (Team t : allTeams) {
            if (t.getDoctor() != null && t.getDoctor().getId() == doctorId && t.getTeamId() != teamId) {
                log.warn("⚠️ Docteur ID={} est déjà affecté à l'équipe ID={}", doctorId, t.getTeamId());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("⚠️ Ce docteur est déjà affecté à une autre équipe !");
            }
        }

        // Affectation du docteur
        team.setDoctor(doctor);
        teamService.updateTeam(teamId, team);
        log.info("✅ Docteur ID={} affecté avec succès à l'équipe ID={}", doctorId, teamId);

        return ResponseEntity.ok(team);
    }



    ///////////

    ///////////
    @PutMapping("/{teamId}/removePlayer")
    public ResponseEntity<?> removePlayerFromTeam(@PathVariable int teamId, @RequestBody Map<String, Integer> payload) {
        int playerId = payload.getOrDefault("playerId", -1);
        if (playerId == -1) {
            return ResponseEntity.badRequest().body(Map.of("error", "❌ `playerId` est requis !"));
        }

        Optional<Team> teamOpt = teamService.getTeamById(teamId);
        Optional<User> playerOpt = userRepository.findById(playerId);

        if (teamOpt.isEmpty() || playerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "❌ Équipe ou joueur introuvable !"));
        }

        Team team = teamOpt.get();
        User player = playerOpt.get();

        if (!team.getPlayers().contains(player)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "⚠️ Ce joueur n'appartient pas à cette équipe !"));
        }

        // 🔥 Supprimer le joueur de l'équipe
        player.setTeam(null);
        userRepository.save(player);
        team.getPlayers().remove(player);
        teamService.updateTeam(teamId, team);

        log.info("🗑 Joueur ID={} retiré de l'équipe ID={}", playerId, teamId);

        // ✅ Retourner un objet JSON valide
        return ResponseEntity.ok(Map.of(
                "message", "✅ Joueur retiré avec succès.",
                "team", team
        ));
    }


    ///
    // ✅ Supprimer une équipe
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable int id) {
        log.info("🔍 Requête DELETE reçue pour supprimer l'équipe ID={}", id);

        boolean deleted = teamService.deleteTeam(id);
        if (deleted) {
            log.info("✅ Équipe ID={} supprimée avec succès.", id);
            return ResponseEntity.ok(Map.of("message", "✅ Équipe supprimée avec succès."));
        } else {
            log.warn("❌ Échec de suppression, équipe ID={} introuvable.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "❌ Équipe introuvable."));
        }
    }


}


