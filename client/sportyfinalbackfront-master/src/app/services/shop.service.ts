import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Shop } from '../models/shop';  // Adjust the path based on your project structure

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private apiUrl = 'http://localhost:8090/shops';  // Your Spring Boot API URL

  constructor(private http: HttpClient) {}

  // Get all shops
  getAllShops(): Observable<Shop[]> {
    return this.http.get<Shop[]>(`${this.apiUrl}/all`);
  }

  // Get a single shop by its ID
  getShopById(id: number): Observable<Shop> {
    return this.http.get<Shop>(`${this.apiUrl}/${id}`);
  }

  // Add a new shop
  addShop(shop: Shop): Observable<Shop> {
    return this.http.post<Shop>(`${this.apiUrl}/add`, shop, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  // Update an existing shop
  updateShop(id: number, shop: Shop): Observable<Shop> {
    return this.http.put<Shop>(`${this.apiUrl}/update/${id}`, shop, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  // Delete a shop
  deleteShop(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
