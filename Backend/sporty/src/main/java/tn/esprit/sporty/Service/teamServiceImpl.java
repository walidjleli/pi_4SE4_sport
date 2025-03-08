package tn.esprit.sporty.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // ✅ Ajout pour les logs
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.Team;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.TeamRepository;
import tn.esprit.sporty.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j // ✅ Permet d'utiliser log.info() pour le debug
public class teamServiceImpl implements IteamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public List<Team> getAllTeams() {
        List<Team> teams = teamRepository.findAll();

        // 🚨 Charger uniquement les données nécessaires pour éviter la boucle infinie
        teams.forEach(team -> {
            team.setPlayers(null); // Ne pas inclure la liste complète des joueurs
        });

        return teams;
    }


    @Override
    public Optional<Team> getTeamById(int id) {
        log.info("🔎 Recherche de l'équipe ID={}", id);
        return teamRepository.findById(id);
    }

    @Override
    public Team createTeam(Team team) {
        log.info("✅ Création d'une équipe : {}", team.getTeamName());

        if (team.getPlayers() != null && !team.getPlayers().isEmpty()) {
            List<Integer> playerIds = team.getPlayers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList());

            List<User> players = userRepository.findAllById(playerIds);
            team.setPlayers(players);
        }

        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(int id, Team teamDetails) {
        return teamRepository.findById(id)
                .map(existingTeam -> {
                    log.info("🔄 Mise à jour de l'équipe ID={}", id);

                    if (teamDetails.getTeamName() != null) existingTeam.setTeamName(teamDetails.getTeamName());
                    if (teamDetails.getSubgroups() != null) existingTeam.setSubgroups(teamDetails.getSubgroups());
                    if (teamDetails.getPlayers() != null) existingTeam.setPlayers(teamDetails.getPlayers());
                    if (teamDetails.getCoach() != null) existingTeam.setCoach(teamDetails.getCoach());
                    if (teamDetails.getDoctor() != null) existingTeam.setDoctor(teamDetails.getDoctor());

                    return teamRepository.save(existingTeam);
                }).orElse(null);
    }

    @Override
    public boolean deleteTeam(int id) {
        try {
            Optional<Team> teamOpt = teamRepository.findById(id);

            if (teamOpt.isEmpty()) {
                log.warn("❌ Échec de suppression, équipe ID={} introuvable", id);
                return false;
            }

            Team team = teamOpt.get();

            // 🔥 Dissocier les joueurs avant suppression
            for (User player : team.getPlayers()) {
                player.setTeam(null);
                userRepository.save(player);
            }
            team.getPlayers().clear();

            // 🔥 Vérifier et retirer le coach
            if (team.getCoach() != null) {
                team.setCoach(null);
            }

            // 🔥 Vérifier et retirer le docteur
            if (team.getDoctor() != null) {
                team.setDoctor(null);
            }

            // 🔥 Sauvegarder les modifications avant suppression
            teamRepository.save(team);

            // 🔥 Supprimer l'équipe après nettoyage
            log.info("🗑 Suppression de l'équipe ID={}", id);
            teamRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            log.error("❌ ERREUR lors de la suppression de l'équipe ID={} : {}", id, e.getMessage(), e);
            return false;
        }
    }



    // ✅ Méthode pour ajouter un joueur à une équipe
    public Team addPlayerToTeam(int teamId, int playerId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        Optional<User> playerOpt = userRepository.findById(playerId);

        if (teamOpt.isEmpty() || playerOpt.isEmpty()) {
            log.error("❌ Échec : Équipe ID={} ou Joueur ID={} introuvable", teamId, playerId);
            return null;
        }

        Team team = teamOpt.get();
        User player = playerOpt.get();

        if (team.getPlayers().contains(player)) {
            log.warn("⚠️ Joueur ID={} est déjà dans l'équipe ID={}", playerId, teamId);
            return null;
        }

        team.getPlayers().add(player);
        return teamRepository.save(team);
    }

    // ✅ Méthode pour affecter un coach à une équipe
    public Team assignCoachToTeam(int teamId, int coachId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        Optional<User> coachOpt = userRepository.findById(coachId);

        if (teamOpt.isEmpty() || coachOpt.isEmpty()) {
            log.error("❌ Échec : Équipe ID={} ou Coach ID={} introuvable", teamId, coachId);
            return null;
        }

        Team team = teamOpt.get();
        User coach = coachOpt.get();

        if (!coach.getRole().equals("COACH")) {
            log.error("❌ L'utilisateur ID={} n'est pas un coach", coachId);
            return null;
        }

        team.setCoach(coach);
        return teamRepository.save(team);
    }

    // ✅ Méthode pour affecter un docteur à une équipe
    public Team assignDoctorToTeam(int teamId, int doctorId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        Optional<User> doctorOpt = userRepository.findById(doctorId);

        if (teamOpt.isEmpty() || doctorOpt.isEmpty()) {
            log.error("❌ Échec : Équipe ID={} ou Docteur ID={} introuvable", teamId, doctorId);
            return null;
        }

        Team team = teamOpt.get();
        User doctor = doctorOpt.get();

        if (!doctor.getRole().equals("DOCTOR")) {
            log.error("❌ L'utilisateur ID={} n'est pas un docteur", doctorId);
            return null;
        }

        team.setDoctor(doctor);
        return teamRepository.save(team);
    }

    public boolean removeDoctorFromTeam(int teamId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) {
            log.warn("❌ Impossible de supprimer : Équipe ID={} introuvable", teamId);
            return false;
        }

        Team team = teamOpt.get();
        if (team.getDoctor() == null) {
            log.warn("⚠️ Aucun docteur à supprimer pour l'équipe ID={}", teamId);
            return false;
        }

        team.setDoctor(null);
        teamRepository.save(team);
        log.info("🗑 Docteur retiré de l'équipe ID={}", teamId);
        return true;
    }

    public boolean removeCoachFromTeam(int teamId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) {
            log.warn("❌ Impossible de supprimer : Équipe ID={} introuvable", teamId);
            return false;
        }

        Team team = teamOpt.get();
        if (team.getCoach() == null) {
            log.warn("⚠️ Aucun coach à supprimer pour l'équipe ID={}", teamId);
            return false;
        }

        team.setCoach(null);
        teamRepository.save(team);
        log.info("🗑 Coach retiré de l'équipe ID={}", teamId);
        return true;
    }

    // ✅ Retirer un joueur d'une équipe
    public boolean removePlayerFromTeam(int teamId, int playerId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        Optional<User> playerOpt = userRepository.findById(playerId);

        if (teamOpt.isEmpty() || playerOpt.isEmpty()) {
            log.warn("❌ Impossible de supprimer : Équipe ID={} ou Joueur ID={} introuvable", teamId, playerId);
            return false;
        }

        Team team = teamOpt.get();
        User player = playerOpt.get();

        if (!team.getPlayers().contains(player)) {
            log.warn("⚠️ Le joueur ID={} n'est pas dans l'équipe ID={}", playerId, teamId);
            return false;
        }

        player.setTeam(null);
        userRepository.save(player);
        team.getPlayers().remove(player);
        teamRepository.save(team);

        log.info("🗑 Joueur ID={} retiré de l'équipe ID={}", playerId, teamId);
        return true;
    }

}
