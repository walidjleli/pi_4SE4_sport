package tn.esprit.sporty.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.PushupResult;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.PushupResultRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PushupResultService {

    @Autowired
    private PushupResultRepository repo;

    public void saveResult(User user, int newScore) {
        Optional<PushupResult> existing = repo.findByUser(user);

        if (existing.isPresent()) {
            PushupResult result = existing.get();
            if (newScore > result.getBestScore()) {
                result.setBestScore(newScore);
                repo.save(result);
            }
        } else {
            PushupResult newResult = new PushupResult();
            newResult.setUser(user);
            newResult.setBestScore(newScore);
            repo.save(newResult);
        }
    }

    public List<PushupResult> getLeaderboard() {
        return repo.findAllByOrderByBestScoreDesc();
    }
}
