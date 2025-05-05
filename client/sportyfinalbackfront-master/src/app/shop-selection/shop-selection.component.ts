import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShopService } from '../services/shop.service';
import { ProductService } from '../services/product.service';
import { Shop } from '../models/shop';

@Component({
  selector: 'app-shop-selection',
  templateUrl: './shop-selection.component.html',
  styleUrls: ['./shop-selection.component.css']
})
export class ShopSelectionComponent implements OnInit {
  shops: Shop[] = [];
  productId!: number;

  constructor(
    private shopService: ShopService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('productId'));
    this.loadShops();
  }

  loadShops() {
    this.shopService.getAllShops().subscribe(data => {
      this.shops = data;
    });
  }

  assignProductToShop(shopId: number) {
    this.productService.assignProductToShop(this.productId, shopId).subscribe(() => {
      alert('Product assigned successfully!');
      this.router.navigate(['/products']); // Redirect back to product list
    });
  }
}
