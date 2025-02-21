package tn.esprit.sporty.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.UserRepository;
import tn.esprit.sporty.Entity.Stats;
import tn.esprit.sporty.Repository.StatsRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements IStatService {
    private final StatsRepository statsRepository;
    private final UserRepository userRepository;
    @Override
    public Stats saveStatistics(Long userId, Stats statistics) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (statsRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User already has statistics. Update instead.");
        }

        statistics.setUser(user);
        return statsRepository.save(statistics);
    }

    @Override
    public List<Stats> getAllStatistics() {
        return statsRepository.findAll();
    }
    @Override
    public Optional<Stats> getStatisticsByUserId(Long userId) {
        return statsRepository.findByUserId(userId);
    }
    @Override
    public Optional<Stats> getStatisticsById(Integer id) {
        return statsRepository.findById(id);
    }

    @Override
    public Stats updateStatistics(Integer id, Stats statistics) {
        return statsRepository.findById(id).map(existingStats -> {
            existingStats.setGoalsScored(statistics.getGoalsScored());
            existingStats.setAssists(statistics.getAssists());
            existingStats.setMinutesPlayed(statistics.getMinutesPlayed());
            existingStats.setInterceptions(statistics.getInterceptions());
            existingStats.setSuccessfulPasses(statistics.getSuccessfulPasses());
            return statsRepository.save(existingStats);
        }).orElseThrow(() -> new RuntimeException("Statistics not found with id: " + id));
    }

    @Override
    public void deleteStatistics(Integer id) {
        statsRepository.deleteById(id);
    }
}
