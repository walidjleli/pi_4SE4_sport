import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product';
import { FootballProductsCategory } from '../models/football-products-category'; // Import the enum

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.css']
})
export class ProductUpdateComponent implements OnInit {
  product: Product = {
    idProduct: 0,
    productName: '',
    description: '',
    stockquantity: 0,
    creationDate: '',
    brand: '',
    price: 0,
    footballProductsCategory: FootballProductsCategory.FOOTBALL_SHOES // Default value
  };

  categoryList = Object.values(FootballProductsCategory); // Get enum values for category dropdown

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadProduct(id);
    }
  }

  loadProduct(id: string): void {
    this.productService.getProductById(Number(id)).subscribe({
      next: (data) => {
        this.product = data;
      },
      error: (err) => {
        console.error('Error fetching product:', err);
      }
    });
  }

  updateProduct(): void {
    if (this.product.idProduct) {
      this.productService.updateProduct(this.product.idProduct, this.product).subscribe({
        next: () => {
          alert('Product updated successfully!');
          this.router.navigate(['/products']);
        },
        error: (err) => {
          console.error('Error updating product:', err);
        }
      });
    } else {
      console.error('Product ID is missing');
    }
  }
  
}
