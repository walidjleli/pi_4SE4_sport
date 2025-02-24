package tn.esprit.sporty.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.Subgroup;
import tn.esprit.sporty.Service.ISubgroupService;

import java.util.List;

@RestController
@RequestMapping("/api/subgroups")
@CrossOrigin("*") // Allow requests from any frontend
public class SubgroupController {

    private final ISubgroupService subgroupService;

    public SubgroupController(ISubgroupService subgroupService) {
        this.subgroupService = subgroupService;
    }

    // ✅ CREATE Subgroup
    @PostMapping("/create")
    public ResponseEntity<Subgroup> createSubgroup(@RequestBody Subgroup subgroup) {
        Subgroup createdSubgroup = subgroupService.createSubgroup(subgroup);
        return ResponseEntity.ok(createdSubgroup);
    }

    // ✅ GET ALL Subgroups
    @GetMapping("/all")
    public ResponseEntity<List<Subgroup>> getAllSubgroups() {
        return ResponseEntity.ok(subgroupService.getAllSubgroups());
    }

    // ✅ GET Subgroup by ID
    @GetMapping("/{id}")
    public ResponseEntity<Subgroup> getSubgroupById(@PathVariable int id) {
        return ResponseEntity.ok(subgroupService.getSubgroupById(id));
    }

    // ✅ UPDATE Subgroup
    @PutMapping("/update/{id}")
    public ResponseEntity<Subgroup> updateSubgroup(@PathVariable int id, @RequestBody Subgroup subgroup) {
        Subgroup updatedSubgroup = subgroupService.updateSubgroup(id, subgroup);
        return ResponseEntity.ok(updatedSubgroup);
    }

    // ✅ DELETE Subgroup
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubgroup(@PathVariable int id) {
        subgroupService.deleteSubgroup(id);
        return ResponseEntity.ok("Subgroup deleted successfully");
    }
}
