package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.Stats;

import java.util.List;
import java.util.Optional;

public interface IStatService {
    Stats saveStatistics(Integer userId,Stats statistics);


    List<Stats> getAllStatistics();


    Optional<Stats> getStatisticsById(Integer id);
    Optional<Stats> getStatisticsByUserId(Integer id);
    Stats updateStatistics(Integer id, Stats statistics);
    void deleteStatistics(Integer id);
}
