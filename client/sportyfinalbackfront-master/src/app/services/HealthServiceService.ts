import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Health } from '../models/Health';



@Injectable({
  providedIn: 'root'
})
export class HealthServiceService {
  private baseUrl = 'http://localhost:8080/Health';

  constructor(private http: HttpClient) { }

   /*   addHealth(data: any): Observable<any> {
        return this.http.post('http://localhost:8089/Sporty/Health/add', data);
      }*/ 
  
      addHealth(data: any, file: File): Observable<any> {
        const formData = new FormData();
        formData.append('health', new Blob([JSON.stringify(data)], {
          type: 'application/json'
        }));
        if (file) {
          formData.append('file', file);
        }
        return this.http.post(`${this.baseUrl}/add`, formData);
      }

      updateHealth(data: any, file?: File): Observable<any> {
        const formData = new FormData();
        formData.append('health', new Blob([JSON.stringify(data)], {
          type: 'application/json'
        }));
        if (file) {
          formData.append('file', file);
        }
        return this.http.post(`${this.baseUrl}/update`, formData);
      }
      
    getHealthByID(id:Number): Observable<any>{
      return this.http.get(`http://localhost:8089/Sporty/Health/${id}`);
 
    }
    
      getHealthlist(): Observable<any> {
        return this.http.get<Health[]>('http://localhost:8089/Sporty/Health/All');
      }
    
      deleteHealth(id : number): Observable<any> {
        return this.http.delete(`http://localhost:8089/Sporty/Health/delete/${id}`);
      }
      getHealthByCondition(userCondition:String): Observable<any>{
          return this.http.get(`http://localhost:8089/Sporty/Health/get/${userCondition}`);
      }
}
