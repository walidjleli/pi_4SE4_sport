package tn.esprit.sporty.Service;





import tn.esprit.sporty.Entity.PhoneNumber;
import tn.esprit.sporty.Entity.Product;
import tn.esprit.sporty.Entity.Review;
import tn.esprit.sporty.Entity.Whishlist;

import java.util.List;
import java.util.Map;

public interface IProductService {
    Product addProduct(Product product);
    Product retrieveProduct(Long idProduct);
    List<Product> retrieveAll();
    void deleteProduct(Long idProduct);
    Product updateProduct(Product product);
   Product assignProductToShop(Long productId, Long shopId);
    List<Product> getProductsByPriceRange(float minPrice,float maxPrice);
    List<Product> searchProductsByName(String productName);
    public Map<String, Double> getCategoryDistribution();
    public Double getAveragePrice();
    public Double calculateAverageStockQuantity();
   Whishlist addProductToWishlist(Long wishlistId, Long productId);
   Whishlist removeProductFromWishlist(Long wishlistId, Long productId);
    List<Product> getWishlistProducts(Long wishlistId);
    Review addReview(Long productId, int rating);
    List<Review> getReviews(Long productId);
    Review updateReview(Long reviewId, int rating);
    PhoneNumber savePhoneNumber(PhoneNumber phoneNumber);
    public List<String> getAllNumbers();




}
