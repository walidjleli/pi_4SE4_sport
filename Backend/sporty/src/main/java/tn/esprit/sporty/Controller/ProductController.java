package tn.esprit.sporty.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import tn.esprit.sporty.Entity.Comment;
import tn.esprit.sporty.Entity.PhoneNumber;
import tn.esprit.sporty.Entity.Product;
import tn.esprit.sporty.Entity.Review;
import tn.esprit.sporty.Repository.IProductRepository;
import tn.esprit.sporty.Service.IProductService;
import tn.esprit.sporty.Service.PredictionService;
import tn.esprit.sporty.Repository.ICommentRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final IProductService productService;
    private final ICommentRepository commentRepository;
    private final IProductRepository productRepository;
    private final PredictionService predictionService;
    @Autowired
    private RestTemplate restTemplate;



    // Existing methods
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> retrieveProduct(@PathVariable Long id) {
        Product product = productService.retrieveProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> retrieveAll() {
        return ResponseEntity.ok(productService.retrieveAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        product.setIdProduct(id);

        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }
    @PutMapping("/{productId}/assign-shop/{shopId}")
    public ResponseEntity<Product> assignProductToShop(@PathVariable Long productId, @PathVariable Long shopId) {
        Product updatedProduct = productService.assignProductToShop(productId, shopId);
        return ResponseEntity.ok(updatedProduct);
    }
    @GetMapping("/filterByPrice")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam("minPrice") float minPrice,
            @RequestParam("maxPrice") float maxPrice) {

        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);

        // Return the list of products wrapped in a ResponseEntity
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();  // If no products found, return 204 No Content
        }
        return ResponseEntity.ok(products);  // Return the filtered products with 200 OK status
    }
    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam("name") String productName) {
        return productService.searchProductsByName(productName);
    }
    @GetMapping("/category-distribution")
    public Map<String, Double> getCategoryDistribution() {
        return productService.getCategoryDistribution();
    }
    // Add product to wishlist
    @GetMapping("/average-price")
    public Double getAveragePrice() {
        return productService.getAveragePrice();
    }
    @GetMapping("/average-stock")
    public ResponseEntity<Double> getAverageStockQuantity() {
        return ResponseEntity.ok(productService.calculateAverageStockQuantity());
    }
    // Add product to wishlist
    @PostMapping("/{wishlistId}/add-to-wishlist/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable Long wishlistId, @PathVariable Long productId) {
        try {
            return ResponseEntity.ok(productService.addProductToWishlist(wishlistId, productId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Remove product from wishlist
    @DeleteMapping("/{wishlistId}/remove-from-wishlist/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable Long wishlistId, @PathVariable Long productId) {
        try {
            return ResponseEntity.ok(productService.removeProductFromWishlist(wishlistId, productId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all products in a wishlist
    @GetMapping("/{wishlistId}/wishlist-products")
    public ResponseEntity<?> getWishlistProducts(@PathVariable Long wishlistId) {
        try {
            return ResponseEntity.ok(productService.getWishlistProducts(wishlistId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/{productId}/reviews")
    public Review addReview(@PathVariable Long productId, @RequestParam int rating) {
        return productService.addReview(productId, rating);
    }

    // Method to update a review
    @PutMapping("/reviews/{reviewId}")
    public Review updateReview(@PathVariable Long reviewId, @RequestParam int rating) {
        return productService.updateReview(reviewId, rating);
    }

    // Method to get reviews of a product
    @GetMapping("/{productId}/reviews")
    public List<Review> getReviews(@PathVariable Long productId) {
        return productService.getReviews(productId);
    }
    // Add a comment to a product
    @PostMapping("/{productId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(@PathVariable Long productId, @RequestBody Comment comment) {
        // Fetch the product by its ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Filter the comment text for bad words before saving
        String filteredText = filterBadWords(comment.getText());
        comment.setText(filteredText); // Set the filtered text

        // Associate the comment with the product
        comment.setProduct(product);

        // Save and return the comment
        return commentRepository.save(comment);
    }

    // Like a comment
    @PutMapping("/comments/{commentId}/like")
    public Comment likeComment(@PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Simple toggle logic (simulate per request)
        if (comment.getLikes() > 0) {
            comment.setLikes(comment.getLikes() - 1);
        } else {
            comment.setLikes(comment.getLikes() + 1);
            if (comment.getDislikes() > 0) {
                comment.setDislikes(comment.getDislikes() - 1);
            }
        }

        return commentRepository.save(comment);
    }


    // Dislike a comment
    @PutMapping("/comments/{commentId}/dislike")
    public Comment dislikeComment(@PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getDislikes() > 0) {
            comment.setDislikes(comment.getDislikes() - 1);
        } else {
            comment.setDislikes(comment.getDislikes() + 1);
            if (comment.getLikes() > 0) {
                comment.setLikes(comment.getLikes() - 1);
            }
        }

        return commentRepository.save(comment);
    }

    @GetMapping("/product/{productId}")
    public List<Comment> getCommentsByProduct(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return commentRepository.findByProduct(product);
    }
    private String filterBadWords(String content) {
        String apiUrl = "https://www.purgomalum.com/service/plain?text=" + UriUtils.encode(content, StandardCharsets.UTF_8);

        try {
            // Call the BadWord API
            String filteredContent = restTemplate.getForObject(apiUrl, String.class);

            // Decode the URL-encoded response
            return URLDecoder.decode(filteredContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error calling BadWord API: " + e.getMessage());
            return content; // Fallback to original comment if API fails
        }
    }
    @PostMapping("/addphonenumber")
    public PhoneNumber save(@RequestBody PhoneNumber phoneNumber) {
        return productService.savePhoneNumber(phoneNumber);
    }
    @GetMapping("/recommend")
    public List<Map<String, Object>> recommendProducts(@RequestParam int age, @RequestParam double budget) {
        List<Product> products = productRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Product p : products) {
            double prediction = predictionService.getPrediction(
                    p.getBrand(),
                    p.getPrice(),
                    p.getFootballProductsCategory().name(),
                    age,
                    budget
            );

            Map<String, Object> productMap = new HashMap<>();
            productMap.put("productName", p.getProductName()); // only product name
            productMap.put("predicted_acceptance_percentage", prediction); // prediction

            result.add(productMap);
        }

        return result;
    }


}




    // New method to assign a product to a shop


