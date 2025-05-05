import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { Review } from '../models/review';
import { Comment } from '../models/Comment.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8090/products'; // Adjust backend URL

  constructor(private http: HttpClient) {}

  // Get all products
  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/all`);
  }

  // Get product by ID
  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  // Add a new product (sending JSON instead of FormData)
  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/add`, product);
  }

  // Update an existing product (sending JSON instead of FormData)
  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/update/${id}`, product);
  }

  // Delete a product
  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
  assignProductToShop(productId: number, shopId: number): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/${productId}/assign-shop/${shopId}`, {});
  }
  getProductsByPriceRange(minPrice: number, maxPrice: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/filterByPrice?minPrice=${minPrice}&maxPrice=${maxPrice}`);
  }
  searchProductsByName(productName: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/search?name=${productName}`);
  }
  getCategoryDistribution(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/category-distribution`);
  }
    // Get average price of all products
    getAveragePrice(): Observable<number> {
      return this.http.get<number>(`${this.apiUrl}/average-price`);
    }
    getAverageStockQuantity(): Observable<number> {
      return this.http.get<number>(`${this.apiUrl}/average-stock`);
    }
    addProductToWishlist(wishlistId: number, productId: number): Observable<any> {
      return this.http.post<any>(`${this.apiUrl}/${wishlistId}/add-to-wishlist/${productId}`, {});
    }
  
    // Remove product from wishlist
    removeProductFromWishlist(wishlistId: number, productId: number): Observable<any> {
      return this.http.delete<any>(`${this.apiUrl}/${wishlistId}/remove-from-wishlist/${productId}`);
    }
  
    // Get all products in a wishlist
    getWishlistProducts(wishlistId: number): Observable<Product[]> {
      return this.http.get<Product[]>(`${this.apiUrl}/${wishlistId}/wishlist-products`);
    }
    // Add review to a product
addReview(productId: number, rating: number): Observable<Review> {
  return this.http.post<Review>(`${this.apiUrl}/${productId}/reviews?rating=${rating}`, null);
}

// Update an existing review
updateReview(reviewId: number, rating: number): Observable<Review> {
  return this.http.put<Review>(`${this.apiUrl}/reviews/${reviewId}`, { rating });
}

// Get all reviews for a product
getReviews(productId: number): Observable<Review[]> {
  return this.http.get<Review[]>(`${this.apiUrl}/${productId}/reviews`);
}
getCommentsByProduct(productId: number): Observable<Comment[]> {
  return this.http.get<Comment[]>(`${this.apiUrl}/product/${productId}`);
}

addComment(productId: number, comment: Comment): Observable<Comment> {
  return this.http.post<Comment>(`${this.apiUrl}/${productId}/comments`, comment);
}

// Delete a comment by ID (you may want to implement this in the backend)
deleteComment(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/comments/${id}`);
}

// Like a comment
likeComment(id: number): Observable<Comment> {
  return this.http.put<Comment>(`${this.apiUrl}/comments/${id}/like`, {});
}

// Dislike a comment
dislikeComment(id: number): Observable<Comment> {
  return this.http.put<Comment>(`${this.apiUrl}/comments/${id}/dislike`, {});
}
// Get recommended products based on age and budget
getRecommendedProducts(age: number, budget: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/recommend?age=${age}&budget=${budget}`);
}


    
  
 
}

  

