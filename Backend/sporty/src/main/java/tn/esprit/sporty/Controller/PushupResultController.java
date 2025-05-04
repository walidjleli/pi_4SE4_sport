package tn.esprit.sporty.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.PushupResult;
import tn.esprit.sporty.Entity.PushupScoreDTO;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.PushupResultRepository;
import tn.esprit.sporty.Repository.UserRepository;
import tn.esprit.sporty.Service.PushupResultService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/pushups")
@RequiredArgsConstructor
public class PushupResultController {

    private final PushupResultService pushupResultService;
    private final UserRepository userRepository;
    private final PushupResultRepository pushupResultRepository;

    @PostMapping("/submit/{userId}")
    public ResponseEntity<?> submitScore(@PathVariable int userId, @RequestBody Map<String, Integer> request) {
        int score = request.get("score");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PushupResult pushupResult = pushupResultRepository.findByUser(user)
                .orElse(new PushupResult());

        // Si nouveau score > précédent => MAJ
        if (score > pushupResult.getBestScore()) {
            pushupResult.setBestScore(score);
            pushupResult.setUser(user);
            pushupResultRepository.save(pushupResult);
        }

        return ResponseEntity.ok(Map.of("message", "Score saved successfully")); // ✅ retourner un JSON
    }



    @GetMapping("/leaderboard")
    public List<PushupResult> getLeaderboard() {
        return pushupResultService.getLeaderboard();
    }
}
