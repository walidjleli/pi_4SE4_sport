package tn.esprit.sporty.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.*;
import tn.esprit.sporty.Repository.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IShopRepository shopRepository;
    private final IWhishlistRepository whishlistRepository;
    private final IReviewRepository reviewRepository;
    private final IPhoneNumberRepository phoneNumberRepository;



    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product retrieveProduct(Long idProduct) {
        return productRepository.findById(idProduct).orElse(null);
    }

    @Override
    public List<Product> retrieveAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long idProduct) {
        productRepository.deleteById(idProduct);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
@Override
public Product assignProductToShop(Long productId, Long shopId) {
    Optional<Product> productOptional = productRepository.findById(productId);
    Optional<Shop> shopOptional = shopRepository.findById(shopId);

    if (productOptional.isPresent() && shopOptional.isPresent()) {
        Product product = productOptional.get();
        Shop shop = shopOptional.get();

        product.setShop(shop); // Assign the shop to the product
        return productRepository.save(product); // Save the updated product
    } else {
        throw new RuntimeException("Product or Shop not found!");
    }
}
    @Override
    public List<Product> getProductsByPriceRange(float minPrice,float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    @Override
    public List<Product> searchProductsByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }
    @Override
    public Map<String, Double> getCategoryDistribution() {
        List<Product> products = productRepository.findAll();  // Fetch all products

        // Map to store the count of products per category
        Map<String, Long> categoryCount = new HashMap<>();
        // Total number of products
        long totalProducts = products.size();

        // Count products per category
        for (Product product : products) {
            String category = product.getFootballProductsCategory().name();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0L) + 1);
        }

        // Calculate percentage for each category
        Map<String, Double> categoryPercentage = new HashMap<>();
        for (Map.Entry<String, Long> entry : categoryCount.entrySet()) {
            categoryPercentage.put(entry.getKey(), (entry.getValue() / (double) totalProducts) * 100);
        }

        return categoryPercentage;
    }
    @Override
    public Double getAveragePrice() {
        return productRepository.calculateAveragePrice();
    }
    @Override
    public Double calculateAverageStockQuantity() {
        return productRepository.getAverageStockQuantity();
    }
    @Override
    public Whishlist addProductToWishlist(Long wishlistId, Long productId) {
        Whishlist wishlist = whishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (wishlist.getProducts().contains(product)) {
            throw new RuntimeException("Product already in wishlist");
        }

        wishlist.getProducts().add(product);
        return whishlistRepository.save(wishlist);
    }
    @Override
    public Whishlist removeProductFromWishlist(Long wishlistId, Long productId) {
        Whishlist wishlist = whishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!wishlist.getProducts().contains(product)) {
            throw new RuntimeException("Product not in wishlist");
        }

        wishlist.getProducts().remove(product);
        return whishlistRepository.save(wishlist);
    }
    @Override
    public List<Product> getWishlistProducts(Long wishlistId) {
        Whishlist wishlist = whishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        return wishlist.getProducts();
    }
    @Override
    public Review addReview(Long productId, int rating) {
        // Fetch the Product by its id
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create a new Review and set the Product and rating
        Review review = new Review();
        review.setProduct(product);
        review.setRating(rating);

        // Save the Review to the database
        return reviewRepository.save(review);
    }

    public List<Review> getReviews(Long productId) {
        return reviewRepository.findByProductIdProduct(productId);
    }
    @Override

    // Method to update review
    public Review updateReview(Long reviewId, int rating) {
        Optional<Review> existingReview = reviewRepository.findById(reviewId);
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        throw new RuntimeException("Review not found");
    }
    @Override
    public PhoneNumber savePhoneNumber(PhoneNumber phoneNumber) {
        return phoneNumberRepository.save(phoneNumber);
    }
    @Override
    public List<String> getAllNumbers() {
        return phoneNumberRepository.findAll()
                .stream()
                .map(PhoneNumber::getNumber)
                .toList();
    }








}
