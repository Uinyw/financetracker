import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Nutrition } from '../product/product';

@Injectable({
  providedIn: 'root',
})
export class AnalyticsService {

  BASE_URL = "http://localhost:8085"

  constructor(private httpClient: HttpClient) {
  }

  getNutrition(startTime: string, endTime: string): Observable<Nutrition> {
    return this.httpClient.get<Nutrition>(`${this.BASE_URL}/nutrition?startTime=${startTime}&endTime=${endTime}`);
  }
  
}