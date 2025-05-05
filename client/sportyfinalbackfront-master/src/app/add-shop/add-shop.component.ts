import { Component } from '@angular/core';
import { ShopService } from 'src/app/services/shop.service';  // Ensure the path is correct
import { Shop } from 'src/app/models/shop';  // Ensure the path is correct
import { ShopCategory } from 'src/app/models/shop-category';  // Ensure the path is correct

@Component({
  selector: 'app-add-shop',
  templateUrl: './add-shop.component.html',
  styleUrls: ['./add-shop.component.css']
})
export class AddShopComponent {
  shop: Shop = new Shop();  // Initialize a new shop object
  categories = Object.values(ShopCategory); // Dropdown options

  constructor(private shopService: ShopService) {}

  addShop() {
    this.shopService.addShop(this.shop).subscribe({
      next: () => {
        alert('Shop added successfully!');
        this.shop = new Shop(); // Reset form after adding
      },
      error: (err) => {
        console.error('Error adding shop:', err);
      }
    });
  }
}
