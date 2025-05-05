package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.Whishlist;


public interface IWhishlistRepository extends JpaRepository<Whishlist, Long> {
}
