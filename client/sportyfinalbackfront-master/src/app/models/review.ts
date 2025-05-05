export class Review {
    id?: number; // Optional, as it will only be assigned when a review is created
    rating: number; // Rating from 1 to 5
    productId: number; // Product ID to associate the review with a product
  
    constructor(rating: number, productId: number, id?: number) {
      this.rating = rating;
      this.productId = productId;
      if (id) {
        this.id = id; // Optional: if the review has an ID, set it
      }
    }
  }
  