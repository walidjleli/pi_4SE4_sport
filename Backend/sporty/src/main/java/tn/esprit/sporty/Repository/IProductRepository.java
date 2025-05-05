package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.sporty.Entity.Product;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetween(float minPrice, float maxPrice);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    @Query("SELECT AVG(p.price) FROM Product p")
    Double calculateAveragePrice();
    @Query("SELECT AVG(p.stockquantity) FROM Product p")
    Double getAverageStockQuantity();
    List<Product> findAllByOrderByPriceAsc();  // Ascending
    List<Product> findAllByOrderByPriceDesc();


// Descending



}
