import { Component, OnInit } from '@angular/core';
import { ShopService } from 'src/app/services/shop.service';  // Ensure the path is correct
import { Shop } from 'src/app/models/shop';  // Ensure the path is correct
import { Router } from '@angular/router';  // Import Router

@Component({
  selector: 'app-shop-list',
  templateUrl: './shop-list.component.html',
  styleUrls: ['./shop-list.component.css']
})
export class ShopListComponent implements OnInit {
  shops: Shop[] = [];  // Array to hold the list of shops

  constructor(
    private shopService: ShopService,  // Inject ShopService
    private router: Router  // Inject Router
  ) {}

  ngOnInit(): void {
    this.loadShops();  // Load shops when the component is initialized
  }

  loadShops(): void {
    this.shopService.getAllShops().subscribe({
      next: (data) => {
        this.shops = data;  // Assign the fetched data to the shops array
      },
      error: (err) => {
        console.error('Error fetching shops:', err);  // Handle errors
      }
    });
  }

  deleteShop(id: number | undefined): void {
    if (id && confirm('Are you sure you want to delete this shop?')) {
      this.shopService.deleteShop(id).subscribe({
        next: () => {
          alert('Shop deleted successfully!');
          this.loadShops();  // Reload the shop list
        },
        error: (err) => {
          console.error('Error deleting shop:', err);
        }
      });
    } else {
      console.error('Invalid shop ID');
    }
  }

  editShop(id: number | undefined): void {
    if (id) {
      // Navigate to the update page with the shop ID
      this.router.navigate([`/back-office/shopupdate/${id}`]);
    } else {
      console.error('Invalid shop ID');
    }
  }

  addShop(): void {
    // Navigate to the add shop page
    this.router.navigate(['/back-office/shop']);
  }
  viewShopDetails(id: number | undefined): void {
    if (id) {
      this.router.navigate([`/back-office/shop-details/${id}`]);  // Navigate with shop ID
    } else {
      console.error('Invalid shop ID');
    }
  }
}
