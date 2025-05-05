import { Component } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product';
import { FootballProductsCategory } from 'src/app/models/football-products-category';


@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent {
  product: Product = new Product();
  categories = Object.values(FootballProductsCategory); // Dropdown options

  constructor(private productService: ProductService) {}

  addProduct() {
    this.productService.addProduct(this.product).subscribe({
      next: () => {
        alert('Product added successfully!');
        this.product = new Product(); // Reset form after adding
      },
      error: (err) => {
        console.error('Error adding product:', err);
      }
    });
  }
}
