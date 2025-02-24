package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.Subgroup;
import java.util.List;

public interface ISubgroupService {
    Subgroup createSubgroup(Subgroup subgroup);
    List<Subgroup> getAllSubgroups();
    Subgroup getSubgroupById(int id);
    Subgroup updateSubgroup(int id, Subgroup subgroup);
    void deleteSubgroup(int id);
}
