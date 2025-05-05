import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product';
import { Router } from '@angular/router'; // Import Router
import { Review } from '../models/review';
import { Comment } from '../models/Comment.model';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  minPrice: number = 0; // Define minPrice
  maxPrice: number = 0;
  searchQuery: string = '';
  averagePrice: number = 0;
  averageStockQuantity: number = 0;
  wishlistId: number = 1;
  recommendedProducts: any[] = [];
  age: number = 25;
  budget: number = 400;
  reviews: Map<number, Review[]> = new Map();
  newRatings: { [productId: number]: number } = {};
  stars: boolean[] = [false, false, false, false, false];
  // Store new comment inputs per product
newComments: { [productId: number]: string } = {};

// Toggle visibility of comment input form per product
showCommentInput: { [productId: number]: boolean } = {};

// Toggle visibility of comments per product
showComments: { [productId: number]: boolean } = {};

// Store comments for each product
productComments: { [productId: number]: any[] } = {};


  

  constructor(
    private productService: ProductService,
    private router: Router // Inject Router here
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.loadAveragePrice();
    this.loadAverageStock();
    this.loadRecommendations(); 
  }
  loadRecommendations() {
    this.productService.getRecommendedProducts(this.age, this.budget).subscribe((data) => {
      console.log('Recommended Products:', data);
      this.recommendedProducts = data;
    });
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        console.error('Error fetching products:', err);
      }
    });
  }
  loadAveragePrice(): void {
    this.productService.getAveragePrice().subscribe({
      next: (data) => {
        this.averagePrice = data;
      },
      error: (err) => {
        console.error('Error fetching average price:', err);
      }
    });
  }
  loadAverageStock(): void {
    this.productService.getAverageStockQuantity().subscribe({
      next: (data) => {
        this.averageStockQuantity = data;
      },
      error: (err) => {
        console.error('Error fetching average stock quantity:', err);
      }
    });
  }

  deleteProduct(id: number | undefined): void {
    if (id && confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(id).subscribe({
        next: () => {
          alert('Product deleted successfully!');
          this.loadProducts(); // Reload the product list
        },
        error: (err) => {
          console.error('Error deleting product:', err);
        }
      });
    } else {
      console.error('Invalid product ID');
    }
  }

  editProduct(id: number | undefined): void {
    if (id) {
      // Navigate to the update page with the product ID
      this.router.navigate([`/back-office/product-update/${id}`]);
    } else {
      console.error('Invalid product ID');
    }
  }
  addProduct(): void {
    // Navigate to the add product page
    this.router.navigate(['/back-office/addpr']);
  }
  showdetailsProduct(id: number | undefined): void {
    if (id) {
      // Navigate to the update page with the product ID
      this.router.navigate([`/back-office/product-details/${id}`]);
    } else {
      console.error('Invalid product ID');
    }
  }
  goToShopSelection(productId: number) {
    this.router.navigate(['/back-office/shop-selection', productId]);
  }
  filterByPrice(): void {
    if (this.minPrice >= 0 && this.maxPrice >= 0 && this.minPrice <= this.maxPrice) {
      // Call the service method to filter by price range
      this.productService.getProductsByPriceRange(this.minPrice, this.maxPrice).subscribe({
        next: (data) => {
          this.products = data;
           // Update the product list with the filtered products
        },
        error: (err) => {
          console.error('Error filtering products:', err);
        }
      });
    } else {
      alert('Please enter a valid price range.');
    }
  }
  searchProducts(): void {
    if (this.searchQuery.trim() === '') {
      this.loadProducts(); // Use loadProducts instead of getAllProducts
    } else {
      this.productService.searchProductsByName(this.searchQuery).subscribe({
        next: (data) => {
          this.products = data; // Update the product list with search results
        },
        error: (err) => {
          console.error('Error fetching search results:', err);
        },
      });
    }
  }
  addToWishlist(productId: number): void {
    this.productService.addProductToWishlist(this.wishlistId, productId).subscribe({
      next: () => {
        alert('Product added to wishlist!');
      },
      error: (err) => {
        console.error('Error adding product to wishlist:', err);
      }
    });
  }

  removeFromWishlist(productId: number): void {
    this.productService.removeProductFromWishlist(this.wishlistId, productId).subscribe({
      next: () => {
        alert('Product removed from wishlist!');
      },
      error: (err) => {
        console.error('Error removing product from wishlist:', err);
      }
    });
  }

  viewWishlist(): void {
    this.productService.getWishlistProducts(this.wishlistId).subscribe({
      next: (data) => {
        console.log('Wishlist products:', data);
        // You can store the data and display it on the page or navigate to a wishlist page
      },
      error: (err) => {
        console.error('Error fetching wishlist products:', err);
      }
    });
  }
  goToWishlist(): void {
    this.router.navigate(['/back-office/wishlist']);  // Navigate to the wishlist page
  }
  loadReviews(productId: number): void {
    this.productService.getReviews(productId).subscribe((reviews) => {
      this.reviews.set(productId, reviews); // Store reviews in the map
    });
  }

  // Method to handle review submission (will be implemented later)
  submitReview(productId: number, rating: number): void {
    this.productService.addReview(productId, rating).subscribe((review) => {
      this.loadReviews(productId); // Refresh reviews after adding
    });
  }

  // Method to handle review update
  updateReview(reviewId: number, rating: number): void {
    if (rating < 1 || rating > 5) {
      alert('Rating must be between 1 and 5');
      return;
    }
    this.productService.updateReview(reviewId, rating).subscribe((review) => {
      this.reviews.forEach((reviews, productId) => {
        if (reviews.some((r) => r.id === reviewId)) {
          this.loadReviews(productId);
        }
      });
    });
  }
  selectRating(productId: number, rating: number): void {
    this.newRatings[productId] = rating;
    // Update stars visually
    this.stars = this.stars.map((_, index) => index < rating);
    // Submit the review automatically when a star is selected
    this.submitReview(productId, rating);
  }
  toggleCommentInput(productId: number): void {
    this.showCommentInput[productId] = !this.showCommentInput[productId];
  }
  
  toggleComments(productId: number): void {
    // If comments are not loaded yet, load them
    if (!this.productComments[productId]) {
      this.loadComments(productId);
    }
    // Toggle visibility
    this.showComments[productId] = !this.showComments[productId];
  }
  
  
  loadComments(productId: number): void {
    this.productService.getCommentsByProduct(productId).subscribe({
      next: (comments) => {
        // Ensure comments are stored correctly for the productId
        this.productComments[productId] = comments;
      },
      error: (err) => console.error('Error loading comments:', err)
    });
  }
  
  
  submitComment(productId: number): void {
    const text = this.newComments[productId];  // Get the comment text
    if (!text || text.trim() === '') return;
  
    // Create a new Comment object with 'text' and default values for likes and dislikes
    const comment: Comment = {
      text: text,      // The comment text
      likes: 0,        // Default likes value
      dislikes: 0,     // Default dislikes value
      product: { idProduct: productId }  // Assuming you want to associate the comment with a product
    };
  
    // Call the service to add the comment
    this.productService.addComment(productId, comment).subscribe({
      next: () => {
        this.newComments[productId] = '';  // Clear the input field after submission
        this.loadComments(productId);      // Refresh the list of comments
      },
      error: (err) => console.error('Error submitting comment:', err)  // Error handling
    });
  }
  
  
  likeComment(commentId: number, productId: number): void {
    this.productService.likeComment(commentId).subscribe(() => {
      this.loadComments(productId);
    });
  }
  
  dislikeComment(commentId: number, productId: number): void {
    this.productService.dislikeComment(commentId).subscribe(() => {
      this.loadComments(productId);
    });
  }
  
  
  
 
}
  
  

