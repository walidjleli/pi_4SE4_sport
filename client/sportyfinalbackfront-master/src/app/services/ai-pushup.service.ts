import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AiPushupService {
  private apiUrl = 'http://127.0.0.1:8000/predict'; // URL du FastAPI

  constructor(private http: HttpClient) {}

  uploadVideo(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(this.apiUrl, formData);
  }
  savePushupScore(userId: number, score: number): Observable<any> {
  return this.http.post(`http://localhost:8090/api/pushups/submit/${userId}`, { score });
}


}
