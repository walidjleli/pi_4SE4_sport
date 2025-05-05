import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
})
export class ProductDetailsComponent implements OnInit {
  product: Product | null = null; // To store product details
  isLoading: boolean = true; // Loading state for the API call
  error: string | null = null; // To store error messages if any

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Fetch the product ID from the route parameter
    const productId = +this.route.snapshot.paramMap.get('id')!;

    // Call the service method to get product details
    this.productService.getProductById(productId).subscribe(
      (product) => {
        this.product = product;
        this.isLoading = false;
      },
      (error) => {
        this.error = 'Product not found';
        this.isLoading = false;
      }
    );
  }
}
