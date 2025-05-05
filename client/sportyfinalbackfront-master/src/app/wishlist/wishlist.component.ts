import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service'; // Import ProductService
import { Product } from '../models/product';  // Assuming Product model

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {
  wishlist: Product[] = []; // Array to hold wishlist items
  wishlistId: number = 1; // Example wishlist ID (you might fetch this dynamically for each user)

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.fetchWishlist(); // Fetch wishlist items when component initializes
  }

  // Fetch wishlist products using the service
  fetchWishlist() {
    this.productService.getWishlistProducts(this.wishlistId).subscribe((products: Product[]) => {
      this.wishlist = products;
    });
  }
}