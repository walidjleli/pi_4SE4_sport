package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.Attendance;
import tn.esprit.sporty.Entity.TrainingSession;
import tn.esprit.sporty.Entity.User;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    Optional<Attendance> findByUserAndTrainingSession(User user, TrainingSession trainingSession);

    List<Attendance> findByTrainingSession_Id(int sessionId);

    List<Attendance> findByUser_Id(int userId);
}
