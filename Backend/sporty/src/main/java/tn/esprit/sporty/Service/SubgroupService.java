package tn.esprit.sporty.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.sporty.Entity.Subgroup;
import tn.esprit.sporty.Entity.Team;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.SubgroupRepository;
import tn.esprit.sporty.Repository.TeamRepository;
import tn.esprit.sporty.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Getter
@Service
@RequiredArgsConstructor
@Slf4j
public class  SubgroupService implements ISubgroupService {

    private final SubgroupRepository subgroupRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    // ✅ Créer un sous-groupe à partir d'une équipe
    @Override
    public Subgroup createSubgroupFromTeam(int teamId, Subgroup subgroup) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) {
            throw new RuntimeException("❌ Équipe introuvable !");
        }

        Team team = teamOpt.get();

        // Ajout d'une vérification pour le nom du sous-groupe
        if (subgroupRepository.existsBySubgroupName(subgroup.getSubgroupName())) {
            throw new RuntimeException("❌ Un sous-groupe avec ce nom existe déjà !");
        }

        subgroup.setTeam(team);
        return subgroupRepository.save(subgroup);
    }


    public List<Subgroup> getSubgroupsByTeamId(int teamId) {
        return subgroupRepository.findByTeam_TeamId( teamId);
    }


    // ✅ Récupérer tous les sous-groupes
    @Override
    public List<Subgroup> getAllSubgroups() {
        return subgroupRepository.findAll();
    }

    // ✅ Récupérer un sous-groupe par ID
    @Override
    public Subgroup getSubgroupById(int id) {
        return subgroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Sous-groupe introuvable !"));
    }

    // ✅ Mettre à jour un sous-groupe
    @Override
    public Subgroup updateSubgroup(int id, Subgroup subgroupDetails) {
        Subgroup subgroup = getSubgroupById(id);
        if (subgroupDetails.getSubgroupName() != null) {
            subgroup.setSubgroupName(subgroupDetails.getSubgroupName());
        }
        return subgroupRepository.save(subgroup);
    }

    // ✅ Supprimer un sous-groupe

    @Override
    public void deleteSubgroup(int id) {
        Optional<Subgroup> subgroupOpt = subgroupRepository.findById(id);
        if (subgroupOpt.isEmpty()) {
            throw new RuntimeException("Sous-groupe introuvable !");
        }

        Subgroup subgroup = subgroupOpt.get();

        log.info("Tentative de suppression du sous-groupe ID={} nommé '{}'", id, subgroup.getSubgroupName());

        try {
            for (User player : subgroup.getPlayers()) {
                player.setSubgroup(null);
                userRepository.save(player);
            }

            subgroupRepository.delete(subgroup);
            log.info("✅ Sous-groupe supprimé avec succès.");
        } catch (Exception e) {
            log.error("❌ Erreur lors de la suppression du sous-groupe ID={}", id, e);
            throw e;  // Propager l'erreur pour la voir en front (code 500)
        }
    }




    // ✅ Affecter un joueur à un sous-groupe
    @Override
    public boolean assignPlayerToSubgroup(int subgroupId, int playerId) {
        Optional<Subgroup> subgroupOpt = subgroupRepository.findById(subgroupId);
        Optional<User> playerOpt = userRepository.findById(playerId);

        if (subgroupOpt.isEmpty() || playerOpt.isEmpty()) {
            throw new RuntimeException("Sous-groupe ou joueur introuvable !");
        }

        Subgroup subgroup = subgroupOpt.get();
        User player = playerOpt.get();

        // Vérifier si le joueur est déjà affecté à un sous-groupe de la même équipe
        if (player.getSubgroup() != null && player.getSubgroup().getTeam().equals(subgroup.getTeam())) {
            throw new RuntimeException("❌ Le joueur est déjà affecté à un autre sous-groupe de cette équipe !");
        }

        // Affecter le joueur au sous-groupe
        player.setSubgroup(subgroup);
        userRepository.save(player);
        return true;
    }
    @Override
    public List<User> findUsersWithoutSubgroup(int teamId) {
        // Récupérer tous les utilisateurs de l'équipe
        List<User> allUsers = userRepository.findByTeam_TeamId(teamId);

        // Filtrer ceux qui n'ont pas de sous-groupe
        return allUsers.stream()
                .filter(user -> user.getSubgroup() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersBySubgroupId(int subgroupId) {
        Optional<Subgroup> subgroupOpt = subgroupRepository.findById(subgroupId);
        if (subgroupOpt.isEmpty()) {
            throw new RuntimeException("Sous-groupe introuvable !");
        }
        return subgroupOpt.get().getPlayers(); // ou getUsers() selon ton modèle
    }
    @Override
    public void removeUserFromSubgroup(int subgroupId, int userId) {
        Optional<Subgroup> subgroupOpt = subgroupRepository.findById(subgroupId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (subgroupOpt.isEmpty() || userOpt.isEmpty()) {
            throw new RuntimeException("❌ Sous-groupe ou utilisateur introuvable !");
        }

        User user = userOpt.get();

        // Vérifie que le joueur appartient bien à ce sous-groupe
        if (user.getSubgroup() == null || user.getSubgroup().getSubgroupId() != subgroupId) {
            throw new RuntimeException("⚠️ L'utilisateur n'appartient pas à ce sous-groupe !");
        }

        user.setSubgroup(null);
        userRepository.save(user);
    }



}
