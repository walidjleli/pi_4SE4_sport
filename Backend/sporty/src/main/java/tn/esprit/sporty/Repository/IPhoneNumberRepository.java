package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.PhoneNumber;


public interface IPhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
}
