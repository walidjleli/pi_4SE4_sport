import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ShopService } from '../services/shop.service';
import { Shop } from '../models/shop';  // Import Shop model

@Component({
  selector: 'app-shop-details',
  templateUrl: './shop-details.component.html',
  styleUrls: ['./shop-details.component.css']
})
export class ShopDetailsComponent implements OnInit {
  shopId!: number;
  shopDetails: Shop | null = null;  // Store the details of the shop

  constructor(
    private route: ActivatedRoute,
    private shopService: ShopService
  ) {}

  ngOnInit(): void {
    // Get shopId from the route params
    this.shopId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.shopId) {
      // Fetch shop details based on shopId
      this.shopService.getShopById(this.shopId).subscribe(
        data => {
          this.shopDetails = data; // Save shop details
        },
        error => {
          console.error('Error fetching shop details', error);
        }
      );
    }
  }
  
}
