package tn.esprit.sporty.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Service.GeminiService;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Optional: allow Angular frontend to access this endpoint
public class ChatController {
    @Autowired
    GeminiService geminiService;

    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String reply = geminiService.getChatbotReply(userMessage);
        return ResponseEntity.ok(Map.of("reply", reply));
    }
}
