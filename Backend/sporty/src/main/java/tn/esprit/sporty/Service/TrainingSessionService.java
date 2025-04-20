package tn.esprit.sporty.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.Attendance;
import tn.esprit.sporty.Entity.Subgroup;
import tn.esprit.sporty.Entity.TrainingSession;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.AttendanceRepository;
import tn.esprit.sporty.Repository.SubgroupRepository;
import tn.esprit.sporty.Repository.TrainingSessionRepository;
import tn.esprit.sporty.Repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final SubgroupRepository subgroupRepository;

    // ✅ Créer une session
    public TrainingSession createSession(TrainingSession session) {
        session.setStartDate(new Date()); // Facultatif
        return trainingSessionRepository.save(session);
    }

    // ✅ Marquer la présence d'un utilisateur
    public Attendance markAttendance(int sessionId, int userId, boolean present) {
        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session introuvable"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Attendance attendance = attendanceRepository.findByUserAndTrainingSession(user, session)
                .orElse(new Attendance(0, user, session, present)); // ou null à la place de 0 si généré

        attendance.setPresent(present);
        return attendanceRepository.save(attendance);
    }

    // ✅ Liste des présences par session
    public List<Attendance> getAttendanceBySession(int sessionId) {
        return attendanceRepository.findByTrainingSession_Id(sessionId);
    }

    // ✅ Liste des présences par utilisateur
    public List<Attendance> getAttendanceByUser(int userId) {
        return attendanceRepository.findByUser_Id(userId);
    }

    public TrainingSession assignSessionToSubgroup(int sessionId, int subgroupId) {
        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session non trouvée"));
        Subgroup subgroup = subgroupRepository.findById(subgroupId)
                .orElseThrow(() -> new RuntimeException("Sous-groupe non trouvé"));

        // Évite les doublons
        if (!session.getSubgroups().contains(subgroup)) {
            session.getSubgroups().add(subgroup);
        }
        if (!subgroup.getTrainingSessions().contains(session)) {
            subgroup.getTrainingSessions().add(session);
        }

        // Sauvegarde des deux
        subgroupRepository.save(subgroup);
        return trainingSessionRepository.save(session);
    }




    public List<Attendance> markAttendanceForSubgroup(int sessionId, int subgroupId) {
        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session non trouvée"));
        Subgroup subgroup = subgroupRepository.findById(subgroupId)
                .orElseThrow(() -> new RuntimeException("Sous-groupe non trouvé"));

        List<User> players = subgroup.getPlayers(); // ou getUsers() selon l'entité

        List<Attendance> attendances = new ArrayList<>();
        for (User player : players) {
            Attendance attendance = attendanceRepository.findByUserAndTrainingSession(player, session)
                    .orElseGet(() -> {
                        Attendance newAttendance = new Attendance();
                        newAttendance.setUser(player);
                        newAttendance.setTrainingSession(session);
                        newAttendance.setPresent(true);
                        return newAttendance;
                    });
            attendance.setPresent(true); // en cas de mise à jour
            attendances.add(attendanceRepository.save(attendance));
        }

        return attendances;
    }
    public List<TrainingSession> getSessionsBySubgroup(int subgroupId) {
        Optional<Subgroup> optionalSubgroup = subgroupRepository.findById(subgroupId);
        if (optionalSubgroup.isPresent()) {
            return new ArrayList<>(optionalSubgroup.get().getTrainingSessions());
        }
        return Collections.emptyList();
    }

    public List<TrainingSession> getAllSessions() {
        return trainingSessionRepository.findAll();
    }




}
