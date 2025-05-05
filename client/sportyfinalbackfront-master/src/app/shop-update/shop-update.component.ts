import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShopService } from 'src/app/services/shop.service';  // Assuming you have a ShopService
import { Shop } from 'src/app/models/shop';  // Import the Shop model
import { ShopCategory } from 'src/app/models/shop-category'; // Import ShopCategory enum

@Component({
  selector: 'app-shop-update',
  templateUrl: './shop-update.component.html',
  styleUrls: ['./shop-update.component.css']
})
export class ShopUpdateComponent implements OnInit {
  shop: Shop = {
    idShop: 0,
    nom: '',
    location: '',
    contactNumber: '',
    openingHours: '',
    closinghours: '',
    email: '',
    description: '',
    categorie: ShopCategory.MEN  // Default category as MEN
  };

  // List of categories for the dropdown
  categoryList = Object.values(ShopCategory);  // Get all the enum values

  constructor(
    private shopService: ShopService,  // Make sure your service is ready
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');  // Get ID from URL
    if (id) {
      this.loadShop(id);  // Load shop if ID is provided
    }
  }

  loadShop(id: string): void {
    this.shopService.getShopById(Number(id)).subscribe({
      next: (data) => {
        this.shop = data;  // Assign the fetched data to the shop object
      },
      error: (err) => {
        console.error('Error fetching shop:', err);
      }
    });
  }

  updateShop(): void {
    if (this.shop.idShop) {  // Check if the ID exists
      this.shopService.updateShop(this.shop.idShop, this.shop).subscribe({
        next: () => {
          alert('Shop updated successfully!');
          this.router.navigate(['/shops']);  // Redirect to shops list page
        },
        error: (err) => {
          console.error('Error updating shop:', err);
        }
      });
    } else {
      console.error('Shop ID is missing');
    }
  }
}
