import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define the input model for shoe price prediction
export interface ShoePriceRequest {
  brand: string;
  category: string;
  size: number;
  rating: number;
  number_of_ratings: number;
}

// Define the output model for shoe price prediction
export interface ShoePriceResponse {
  predicted_shoe_price: number;
}

@Injectable({
  providedIn: 'root'
})
export class ShoePredictionService {

  private apiUrl = 'http://localhost:8000/predict_shoe_price'; // Replace with your FastAPI URL

  constructor(private http: HttpClient) { }

  // Method to send the shoe price prediction request
  predictShoePrice(request: ShoePriceRequest): Observable<ShoePriceResponse> {
    return this.http.post<ShoePriceResponse>(this.apiUrl, request);
  }
}
