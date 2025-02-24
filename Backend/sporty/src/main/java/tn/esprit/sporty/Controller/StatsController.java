package tn.esprit.sporty.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.Stats;
import tn.esprit.sporty.Service.IStatService;
import tn.esprit.sporty.Service.StatsServiceImpl;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/stat")
public class StatsController {
    private final IStatService  statisticsService;

    public StatsController(IStatService statisticsService) {
        this.statisticsService = statisticsService;
    }


    @PostMapping("/statistics/{userId}")
    public ResponseEntity<Stats> createStatistics(@PathVariable Integer userId, @RequestBody Stats statistics) {
        return ResponseEntity.ok(statisticsService.saveStatistics(userId, statistics));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Stats> getStatisticsByUserId(@PathVariable Integer userId) {
        return statisticsService.getStatisticsByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Stats>> getAllStatistics() {
        return ResponseEntity.ok(statisticsService.getAllStatistics());
    }

    @GetMapping("/getStats/{id}")
    public ResponseEntity<Stats> getStatisticsById(@PathVariable Integer id) {
        Optional<Stats> statistics = statisticsService.getStatisticsById(id);
        return statistics.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stats> updateStatistics(@PathVariable Integer id, @RequestBody Stats statistics) {
        return ResponseEntity.ok(statisticsService.updateStatistics(id, statistics));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Integer id) {
        statisticsService.deleteStatistics(id);
        return ResponseEntity.noContent().build();
    }
}
