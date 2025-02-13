package tn.esprit.sporty.Stats.service;

import tn.esprit.sporty.Stats.model.Stats;

import java.util.List;
import java.util.Optional;

public interface IStatService {
    Stats saveStatistics(Long userId,Stats statistics);
    List<Stats> getAllStatistics();
    Optional<Stats> getStatisticsById(Integer id);
    Optional<Stats> getStatisticsByUserId(Long id);
    Stats updateStatistics(Integer id, Stats statistics);
    void deleteStatistics(Integer id);
}
