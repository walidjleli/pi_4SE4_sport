package tn.esprit.sporty.Stats.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Stats.model.Stats;
import tn.esprit.sporty.Stats.service.StatsServiceImpl;

import java.util.List;
import java.util.Optional;

public class StatsController { private final StatsServiceImpl statisticsService;

    public StatsController(StatsServiceImpl statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Stats> createStatistics(@PathVariable Long userId, @RequestBody Stats statistics) {
        return ResponseEntity.ok(statisticsService.saveStatistics(userId, statistics));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Stats> getStatisticsByUserId(@PathVariable Long userId) {
        return statisticsService.getStatisticsByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Stats>> getAllStatistics() {
        return ResponseEntity.ok(statisticsService.getAllStatistics());
    }

    @GetMapping("/{id}")
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
