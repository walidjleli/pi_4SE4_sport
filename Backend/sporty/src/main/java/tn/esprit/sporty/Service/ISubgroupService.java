package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.Subgroup;
import tn.esprit.sporty.Entity.User;

import java.util.List;

public interface ISubgroupService {
    Subgroup createSubgroupFromTeam(int teamId, Subgroup subgroup);
    List<Subgroup> getAllSubgroups();
    Subgroup getSubgroupById(int id);
    Subgroup updateSubgroup(int id, Subgroup subgroup);
    void deleteSubgroup(int id);
    boolean assignPlayerToSubgroup(int subgroupId, int playerId);

    List<Subgroup> getSubgroupsByTeamId(int teamId);

    List<User> findUsersWithoutSubgroup(int teamId);
    List<User> getUsersBySubgroupId(int subgroupId);
    void removeUserFromSubgroup(int subgroupId, int userId);

}
