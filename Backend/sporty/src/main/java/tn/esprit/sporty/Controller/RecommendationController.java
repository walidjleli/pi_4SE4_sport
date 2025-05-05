package tn.esprit.sporty.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.sporty.Entity.Stats;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.UserRepository;
import tn.esprit.sporty.Service.IStatService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class RecommendationController {

    private final UserRepository userRepository;
    private final IStatService statService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/recommend/{userId}")
    public ResponseEntity<?> getIARecommendation(@PathVariable int userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Stats> statsOpt = statService.getStatisticsByUserId(userId); // tu l'as déjà

        if (userOpt.isEmpty() || statsOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Utilisateur ou stats introuvables");
        }

        User user = userOpt.get();
        Stats stats = statsOpt.get();

        if (user.getPoste() == null) {
            return ResponseEntity.badRequest().body("Poste non défini pour ce joueur");
        }

        // Préparer le body JSON pour FastAPI
        Map<String, Object> payload = new HashMap<>();
        payload.put("poste", user.getPoste().name()); // ex: "ATTAQUANT"
        payload.put("goalsScored", stats.getGoalsScored());
        payload.put("assists", stats.getAssists());
        payload.put("minutesPlayed", stats.getMinutesPlayed());
        payload.put("interceptions", stats.getInterceptions());
        payload.put("successfulPasses", stats.getSuccessfulPasses());

        // Appel vers FastAPI
        String url = "http://localhost:8001/predict";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return ResponseEntity.ok(response.getBody());
    }
}

