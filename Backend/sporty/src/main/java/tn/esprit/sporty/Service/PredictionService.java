package tn.esprit.sporty.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.sporty.DTO.PredictionResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class PredictionService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double getPrediction(String brand, double price, String footballCategory, int age, double budget) {
        String url = "http://localhost:8000/predict"; // URL to your FastAPI endpoint

        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("brand", brand);
        requestBody.put("price", price);
        requestBody.put("football_products_category", footballCategory);
        requestBody.put("age", age);
        requestBody.put("budget", budget);

        // Headers (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<PredictionResponse> response = restTemplate.postForEntity(
                url, entity, PredictionResponse.class
        );

        return response.getBody().getPredicted_acceptance_percentage();
    }
}