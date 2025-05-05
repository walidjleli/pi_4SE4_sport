package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.sporty.Entity.Shop;

@Repository
public interface IShopRepository extends JpaRepository<Shop, Long> {
}
