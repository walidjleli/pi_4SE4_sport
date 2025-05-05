import { Component } from '@angular/core';
import { ShoePredictionService, ShoePriceRequest, ShoePriceResponse } from '../services/shoe-prediction.service';  // Use relative path


@Component({
  selector: 'app-shoe-price-prediction',
  templateUrl: './shoe-price-prediction.component.html',
  styleUrls: ['./shoe-price-prediction.component.css']
})
export class ShoePricePredictionComponent {
  // Initialize form input variables
  brand: string = '';
  category: string = '';
  size: number = 0;
  rating: number = 0;
  numberOfRatings: number = 0;

  // Variable to hold the predicted shoe price
  predictedPrice: number | null = null;

  // Inject the service to make API calls
  constructor(private shoePredictionService: ShoePredictionService) {}

  // Method to handle form submission
  onSubmit() {
    const request: ShoePriceRequest = {
      brand: this.brand,
      category: this.category,
      size: this.size,
      rating: this.rating,
      number_of_ratings: this.numberOfRatings
    };

    // Call the service to predict shoe price
    this.shoePredictionService.predictShoePrice(request).subscribe(
      (response: ShoePriceResponse) => {
        // Handle the response and set the predicted price
        this.predictedPrice = response.predicted_shoe_price;
      },
      error => {
        // Handle error case
        console.error('Error predicting shoe price:', error);
        alert('Error predicting shoe price');
      }
    );
  }
}
