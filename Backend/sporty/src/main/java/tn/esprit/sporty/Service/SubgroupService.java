package tn.esprit.sporty.Service;

import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.Subgroup;
import tn.esprit.sporty.Entity.Team;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.SubgroupRepository;
import tn.esprit.sporty.Repository.TeamRepository;
import tn.esprit.sporty.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubgroupService implements ISubgroupService {

    private final SubgroupRepository subgroupRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public SubgroupService(SubgroupRepository subgroupRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.subgroupRepository = subgroupRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Subgroup createSubgroup(Subgroup subgroup) {
        // Fetch the team with its players (eagerly)
        Team team = teamRepository.findById(subgroup.getTeam().getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Team not found!"));

        // Fetch users from the database using their IDs
        List<User> users = subgroup.getUsers().stream()
                .map(user -> userRepository.findById(user.getId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getId())))
                .collect(Collectors.toList());

        // Validate users belong to the team
        for (User user : users) {
            if (!team.getPlayers().contains(user)) {
                throw new IllegalArgumentException("User " + user.getId() + " is not part of the team!");
            }
        }

        // Update the subgroup with managed users
        subgroup.setUsers(users);
        subgroup.setTeam(team); // Ensure the subgroup references the managed team

        return subgroupRepository.save(subgroup);
    }

    @Override
    public List<Subgroup> getAllSubgroups() {
        return subgroupRepository.findAll();
    }

    @Override
    public Subgroup getSubgroupById(int id) {
        return subgroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subgroup not found!"));
    }

    @Override
    public Subgroup updateSubgroup(int id, Subgroup subgroup) {
        Optional<Subgroup> existingSubgroupOpt = subgroupRepository.findById(id);

        if (existingSubgroupOpt.isPresent()) {
            Subgroup existingSubgroup = existingSubgroupOpt.get();

            // Ensure all users are from the same team
            List<User> teamMembers = existingSubgroup.getTeam().getPlayers();
            for (User user : subgroup.getUsers()) {
                if (!teamMembers.contains(user)) {
                    throw new IllegalArgumentException("User " + user.getEmail() + " is not part of the team!");
                }
            }

            existingSubgroup.setSubgroupName(subgroup.getSubgroupName());
            existingSubgroup.setUsers(subgroup.getUsers());

            return subgroupRepository.save(existingSubgroup);
        } else {
            throw new IllegalArgumentException("Subgroup not found!");
        }
    }

    @Override
    public void deleteSubgroup(int id) {
        subgroupRepository.deleteById(id);
    }
}
