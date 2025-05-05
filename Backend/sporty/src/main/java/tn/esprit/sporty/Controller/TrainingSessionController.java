package tn.esprit.sporty.Controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Entity.*;
import tn.esprit.sporty.Service.EmailService;
import tn.esprit.sporty.Service.SubgroupService;
import tn.esprit.sporty.Service.TrainingSessionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainingsessions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;
    private final SubgroupService subgroupService;
    private final EmailService emailService;


    //  Créer une nouvelle session
    @PostMapping
    public ResponseEntity<TrainingSession> createSession(@RequestBody TrainingSession session) {
        TrainingSession createdSession = trainingSessionService.createSession(session);
        return ResponseEntity.ok(createdSession);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<TrainingSession>> getAllTeams() {
        List<TrainingSession> teams = trainingSessionService.getAllSessions();
        return ResponseEntity.ok(teams);
    }

    //  Marquer la présence
    @PutMapping("/{sessionId}/attendance/{userId}")
    public ResponseEntity<Attendance> markAttendance(
            @PathVariable int sessionId,
            @PathVariable int userId,
            @RequestBody AttendanceRequest request
    ) {
        Attendance attendance = trainingSessionService.markAttendance(sessionId, userId, request.present);
        return ResponseEntity.ok(attendance);
    }

    //  Obtenir les présences pour une session
    @GetMapping("/{sessionId}/attendance")
    public ResponseEntity<List<Attendance>> getSessionAttendance(@PathVariable int sessionId) {
        return ResponseEntity.ok(trainingSessionService.getAttendanceBySession(sessionId));
    }

    // ✅ Obtenir les présences pour un utilisateur
    @GetMapping("/user/{userId}/attendance")
    public ResponseEntity<List<Attendance>> getUserAttendance(@PathVariable int userId) {
        return ResponseEntity.ok(trainingSessionService.getAttendanceByUser(userId));
    }

    // ✅ Classe interne pour recevoir le champ "present" dans la requête
    public static class AttendanceRequest {
        public boolean present;
    }

    @PutMapping("/{sessionId}/assign-subgroup/{subgroupId}")
    public ResponseEntity<TrainingSession> assignSessionToSubgroup(
            @PathVariable int sessionId,
            @PathVariable int subgroupId) {
        TrainingSession session = trainingSessionService.assignSessionToSubgroup(sessionId, subgroupId);
        return ResponseEntity.ok(session);
    }


    @GetMapping("/subgroups")
    public ResponseEntity<List<Subgroup>> getAllSubgroups() {
        return ResponseEntity.ok(subgroupService.getAllSubgroups());
    }

    @GetMapping("/{subgroupId}/users")
    public ResponseEntity<List<User>> getUsersBySubgroup(@PathVariable int subgroupId) {
        System.out.println("✅ REQUÊTE REÇUE pour /api/subgroups/" + subgroupId + "/users");
        return ResponseEntity.ok(subgroupService.getUsersBySubgroupId(subgroupId));
    }

    // ✅ Récupérer toutes les sessions associées à un sous-groupe
    @GetMapping("/subgroup/{subgroupId}/sessions")
    public ResponseEntity<List<TrainingSession>> getSessionsBySubgroup(@PathVariable int subgroupId) {
        List<TrainingSession> sessions = trainingSessionService.getSessionsBySubgroup(subgroupId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping
    public ResponseEntity<List<TrainingSession>> getAllSessions() {
        List<TrainingSession> sessions = trainingSessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }
    @PostMapping("/send-absence-email")
    public ResponseEntity<String> sendAbsenceEmail(@RequestBody EmailRequest request) {
        System.out.println("📧 Envoi réel d’un mail à : " + request.getEmail());
        emailService.sendAbsenceNotification(request.getEmail());  // ✅ Envoi réel
        return ResponseEntity.ok("Email envoyé à " + request.getEmail());
    }



    // Classe interne pour recevoir le body JSON avec l'email
    @Data
    public static class EmailRequest {
        private String email;
    }





}
